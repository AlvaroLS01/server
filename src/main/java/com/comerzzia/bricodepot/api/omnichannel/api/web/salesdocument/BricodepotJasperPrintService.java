package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.exception.BadRequestException;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;
import com.comerzzia.omnichannel.service.documentprint.DocumentPrintService;
import com.comerzzia.omnichannel.service.documentprint.jasper.JasperPrintServiceImpl;

/**
 * Custom Jasper print service that mirrors the product implementation but
 * resolves templates from the classpath when they are not available in the
 * external {@code AppInfo} directory. It also adapts the ticket parameter to the
 * legacy package expected by the Jasper templates bundled for Brico Depot.
 */
@Service
@Primary
public class BricodepotJasperPrintService extends JasperPrintServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(BricodepotJasperPrintService.class);

    private static final String CLASSPATH_TEMPLATE_DIRECTORY = "informes/ventas/facturas/";
    private static final String CLASSPATH_TEMPLATE_PATTERN = "classpath*:" + CLASSPATH_TEMPLATE_DIRECTORY + "*.jasper";

    private static final Map<String, String> TEMPLATE_ALIASES;
    static {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("factura", "facturaA4");
        aliases.put("facturaa4", "facturaA4");
        aliases.put("facturaa4_original", "facturaA4_Original");
        aliases.put("facturaa4_pt", "facturaA4_PT");
        aliases.put("facturaa4_ca", "facturaA4_CA");
        aliases.put("facturadevoluciona4_pt", "facturaDevolucionA4_PT");
        aliases.put("facturadevoluciona4_pt_old", "facturaDevolucionA4_PT");
        aliases.put("fs", "facturaA4");
        aliases.put("ft", "facturaA4");
        aliases.put("fr", "facturaA4");
        aliases.put("nc", "facturaA4");
        TEMPLATE_ALIASES = Collections.unmodifiableMap(aliases);
    }

    private final PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
    private final Map<String, File> templateCache = new ConcurrentHashMap<>();
    private volatile Path extractedTemplatesDirectory;

    @Override
    protected Map<String, Object> generateDocParameters(IDatosSesion datosSesion, PrintDocumentDTO printRequest)
            throws ApiException {
        applyTemplateAlias(printRequest);
        Map<String, Object> docParameters = super.generateDocParameters(datosSesion, printRequest);
        applyDefaultTemplateAlias(docParameters);
        if (Boolean.TRUE.equals(printRequest.getCopy())) {
            docParameters.put("esDuplicado", Boolean.TRUE);
        }
        adaptLegacyTicket(docParameters, "ticket");
        adaptLegacyTicket(docParameters, "document");
        return docParameters;
    }

    @Override
    protected File getTemplate(PrintDocumentDTO printRequest, Map<String, Object> docParameters) throws ApiException {
        applyTemplateAlias(printRequest);
        applyDefaultTemplateAlias(docParameters);
        try {
            return super.getTemplate(printRequest, docParameters);
        }
        catch (BadRequestException exception) {
            File fallback = locateTemplateFromClasspath(printRequest, docParameters);
            if (fallback != null) {
                LOGGER.debug("getTemplate() - Using Jasper template {} from classpath fallback", fallback.getAbsolutePath());
                return fallback;
            }
            throw exception;
        }
    }

    private void applyTemplateAlias(PrintDocumentDTO printRequest) {
        if (printRequest == null) {
            return;
        }
        String template = normaliseTemplateName(printRequest.getPrintTemplate());
        if (template != null) {
            printRequest.setPrintTemplate(template);
        }
    }

    private void applyDefaultTemplateAlias(Map<String, Object> docParameters) {
        if (docParameters == null) {
            return;
        }
        Object defaultTemplate = docParameters.get(DocumentPrintService.PARAM_DEFAULT_TEMPLATE);
        if (defaultTemplate instanceof String) {
            String template = normaliseTemplateName((String) defaultTemplate);
            if (template != null) {
                docParameters.put(DocumentPrintService.PARAM_DEFAULT_TEMPLATE, template);
            }
        }
    }

    private String normaliseTemplateName(String template) {
        if (template == null) {
            return null;
        }
        String trimmed = template.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        String alias = TEMPLATE_ALIASES.get(trimmed.toLowerCase(Locale.ROOT));
        return alias != null ? alias : trimmed;
    }

    private void adaptLegacyTicket(Map<String, Object> docParameters, String key) {
        if (docParameters == null) {
            return;
        }
        Object value = docParameters.get(key);
        if (value instanceof com.comerzzia.omnichannel.model.documents.sales.ticket.TicketVentaAbono) {
            com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.TicketVentaAbono adapted =
                    com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.TicketVentaAbono
                            .fromModel((com.comerzzia.omnichannel.model.documents.sales.ticket.TicketVentaAbono) value);
            docParameters.put(key, adapted);
        }
    }

    private File locateTemplateFromClasspath(PrintDocumentDTO printRequest, Map<String, Object> docParameters) {
        String requested = firstNonBlank(printRequest != null ? printRequest.getPrintTemplate() : null,
                docParameters != null ? (String) docParameters.get(DocumentPrintService.PARAM_DEFAULT_TEMPLATE) : null);
        String template = normaliseTemplateName(requested);
        if (!StringUtils.hasText(template)) {
            return null;
        }

        String localeId = docParameters != null ? (String) docParameters.get(DocumentPrintService.PARAM_LOCALE_ID) : null;
        for (String candidate : buildCandidateNames(template, localeId)) {
            File cached = templateCache.get(candidate);
            if (cached != null && cached.exists()) {
                return cached;
            }
            File resolved = resolveTemplate(candidate);
            if (resolved != null) {
                templateCache.put(candidate, resolved);
                return resolved;
            }
        }
        return null;
    }

    private String firstNonBlank(String first, String second) {
        if (StringUtils.hasText(first)) {
            return first;
        }
        if (StringUtils.hasText(second)) {
            return second;
        }
        return null;
    }

    private List<String> buildCandidateNames(String template, String localeId) {
        String extension = getTemplateExtension();
        List<String> candidates = new ArrayList<>();
        if (StringUtils.hasText(localeId)) {
            String normalized = localeId.trim();
            if (!normalized.isEmpty()) {
                String lower = normalized.toLowerCase(Locale.ROOT);
                candidates.add(template + "_" + lower + extension);
                if (lower.length() > 2) {
                    candidates.add(template + "_" + lower.substring(0, 2) + extension);
                }
                String upper = normalized.toUpperCase(Locale.ROOT);
                candidates.add(template + "_" + upper + extension);
            }
        }
        candidates.add(template + extension);
        return candidates;
    }

    private File resolveTemplate(String fileName) {
        try {
            Path directory = ensureTemplatesDirectory();
            Path templatePath = directory.resolve(fileName);
            if (Files.exists(templatePath)) {
                return templatePath.toFile();
            }
            return null;
        }
        catch (IOException exception) {
            throw new DocumentoVentaImpresionException(
                    "No se pudieron preparar las plantillas de impresión personalizadas", exception);
        }
    }

    private Path ensureTemplatesDirectory() throws IOException {
        Path current = this.extractedTemplatesDirectory;
        if (current != null && Files.exists(current)) {
            return current;
        }
        synchronized (this) {
            if (extractedTemplatesDirectory != null && Files.exists(extractedTemplatesDirectory)) {
                return extractedTemplatesDirectory;
            }
            Path classpathDirectory = locateClasspathDirectory();
            if (classpathDirectory != null) {
                extractedTemplatesDirectory = classpathDirectory;
                LOGGER.debug("ensureTemplatesDirectory() - Using Jasper templates directly from classpath directory {}",
                        classpathDirectory);
                return classpathDirectory;
            }
            Path temporaryDirectory = Files.createTempDirectory("bricodepot-jasper-templates");
            copyTemplatesFromClasspath(temporaryDirectory);
            extractedTemplatesDirectory = temporaryDirectory;
            LOGGER.debug("ensureTemplatesDirectory() - Copied Jasper templates to temporary directory {}", temporaryDirectory);
            return temporaryDirectory;
        }
    }

    private Path locateClasspathDirectory() {
        try {
            Resource resource = resourceResolver.getResource("classpath:" + CLASSPATH_TEMPLATE_DIRECTORY);
            if (resource.exists()) {
                File file = resource.getFile();
                if (file.isDirectory()) {
                    return file.toPath();
                }
            }
        }
        catch (IOException | IllegalStateException exception) {
            LOGGER.debug("locateClasspathDirectory() - Unable to resolve classpath Jasper directory", exception);
        }
        return null;
    }

    private void copyTemplatesFromClasspath(Path destination) throws IOException {
        Resource[] resources = resourceResolver.getResources(CLASSPATH_TEMPLATE_PATTERN);
        for (Resource resource : resources) {
            if (!resource.exists()) {
                continue;
            }
            String filename = resource.getFilename();
            if (!StringUtils.hasText(filename)) {
                continue;
            }
            Path target = destination.resolve(Objects.requireNonNull(filename));
            try (InputStream input = resource.getInputStream()) {
                Files.copy(input, target, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}
