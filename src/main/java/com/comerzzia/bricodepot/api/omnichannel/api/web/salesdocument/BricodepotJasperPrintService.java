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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.exception.BadRequestException;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;
import com.comerzzia.omnichannel.service.documentprint.DocumentPrintService;
import com.comerzzia.omnichannel.service.documentprint.jasper.JasperPrintServiceImpl;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Custom {@link JasperPrintServiceImpl} implementation that mirrors the product
 * behaviour but falls back to the Brico specific templates packaged within the
 * application when they are not present in the default location defined in
 * {@code AppInfo}. It also normalises template aliases so that custom template
 * names such as {@code FT} resolve to the expected Jasper files.
 */
@Service
@Primary
public class BricodepotJasperPrintService extends JasperPrintServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(BricodepotJasperPrintService.class);

    private static final String CLASSPATH_TEMPLATE_DIRECTORY = "informes/ventas/facturas/";

    private static final Map<String, String> TEMPLATE_ALIASES;
    static {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("factura", "facturaA4");
        aliases.put("facturaa4", "facturaA4");
        aliases.put("facturaa4_original", "facturaA4_Original");
        aliases.put("facturaa4_pt", "facturaA4_PT");
        aliases.put("facturaa4_ca", "facturaA4_CA");
        aliases.put("facturadevoluciona4_pt", "facturaDevolucionA4_PT");
        aliases.put("facturadevoluciona4_pt_old", "facturaDevolucionA4_PT_OLD");
        aliases.put("fs", "facturaA4");
        aliases.put("ft", "facturaA4");
        aliases.put("fr", "facturaA4");
        aliases.put("nc", "facturaA4");
        TEMPLATE_ALIASES = Collections.unmodifiableMap(aliases);
    }

    private final PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
    private final Map<String, File> templateCache = new ConcurrentHashMap<>();
    private volatile Path extractedTemplatesDirectory;
    private volatile boolean templatesDirectoryIsTemporary;

    @Override
    protected JasperPrint getJasperPrint(IDatosSesion datosSesion, PrintDocumentDTO printRequest) throws ApiException {
        LOGGER.debug("getJasperPrint() - Generando documento con parametros: {}", printRequest);

        Map<String, Object> docParameters = generateDocParameters(datosSesion, printRequest);
        File templateFile = (File) docParameters.get(DocumentPrintService.TEMPLATE_FILE);

        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(templateFile);
            return JasperFillManager.fillReport(jasperReport, docParameters, new JREmptyDataSource());
        }
        catch (JRException exception) {
            String message = String.format("Error al generar el informe Jasper '%s': %s", templateFile, exception.getMessage());
            LOGGER.error("getJasperPrint() - {}", message, exception);
            throw new ApiException(message, exception);
        }
    }

    @Override
    protected File getTemplate(PrintDocumentDTO printRequest, Map<String, Object> docParameters) throws ApiException {
        normaliseTemplateNames(printRequest, docParameters);

        try {
            return super.getTemplate(printRequest, docParameters);
        }
        catch (BadRequestException exception) {
            File fallback = locateTemplateFromClasspath(printRequest, docParameters);
            if (fallback != null) {
                LOGGER.debug("getTemplate() - Using classpath Jasper template {}", fallback.getAbsolutePath());
                return fallback;
            }
            throw exception;
        }
    }

    private void normaliseTemplateNames(PrintDocumentDTO printRequest, Map<String, Object> docParameters) {
        if (printRequest != null) {
            String template = normaliseTemplateName(printRequest.getPrintTemplate());
            if (template != null) {
                printRequest.setPrintTemplate(template);
            }
        }
        if (docParameters != null) {
            Object defaultTemplate = docParameters.get(DocumentPrintService.PARAM_DEFAULT_TEMPLATE);
            if (defaultTemplate instanceof String) {
                String template = normaliseTemplateName((String) defaultTemplate);
                if (template != null) {
                    docParameters.put(DocumentPrintService.PARAM_DEFAULT_TEMPLATE, template);
                }
            }
        }
    }

    private File locateTemplateFromClasspath(PrintDocumentDTO printRequest, Map<String, Object> docParameters) throws ApiException {
        String templateName = null;
        if (printRequest != null && StringUtils.isNotBlank(printRequest.getPrintTemplate())) {
            templateName = printRequest.getPrintTemplate();
        }
        if (StringUtils.isBlank(templateName)) {
            Object defaultTemplate = docParameters != null ? docParameters.get(DocumentPrintService.PARAM_DEFAULT_TEMPLATE) : null;
            if (defaultTemplate instanceof String) {
                templateName = (String) defaultTemplate;
            }
        }
        if (StringUtils.isBlank(templateName)) {
            return null;
        }

        templateName = normaliseTemplateName(templateName);
        String localeId = safeToString(docParameters != null ? docParameters.get(DocumentPrintService.PARAM_LOCALE_ID) : null);
        String countryId = safeToString(docParameters != null ? docParameters.get(DocumentPrintService.PARAM_COUNTRY_ID) : null);

        List<String> candidateFiles = buildCandidateFileNames(templateName, localeId, countryId);
        for (String candidate : candidateFiles) {
            File template = resolveTemplate(candidate);
            if (template != null) {
                return template;
            }
        }
        return null;
    }

    private List<String> buildCandidateFileNames(String template, String localeId, String countryId) {
        List<String> candidates = new ArrayList<>();
        if (StringUtils.isNotBlank(localeId)) {
            String normalisedLocale = localeId.replace(' ', '_');
            candidates.add(template + "_" + normalisedLocale.toUpperCase(Locale.ROOT) + ".jasper");
            candidates.add(template + "_" + normalisedLocale.toLowerCase(Locale.ROOT) + ".jasper");
        }
        if (StringUtils.isNotBlank(countryId)) {
            candidates.add(template + "_" + countryId.toUpperCase(Locale.ROOT) + ".jasper");
            candidates.add(template + "_" + countryId.toLowerCase(Locale.ROOT) + ".jasper");
        }
        candidates.add(template + ".jasper");
        return candidates;
    }

    private File resolveTemplate(String fileName) throws ApiException {
        try {
            Path directory = ensureTemplatesExtracted();
            Path templatePath = directory.resolve(fileName);
            if (Files.exists(templatePath)) {
                return templateCache.computeIfAbsent(fileName, key -> {
                    File file = templatePath.toFile();
                    if (templatesDirectoryIsTemporary) {
                        file.deleteOnExit();
                    }
                    return file;
                });
            }
            return null;
        }
        catch (IOException exception) {
            throw new DocumentoVentaImpresionException(
                    "No se pudieron preparar las plantillas de impresión personalizadas", exception);
        }
    }

    private Path ensureTemplatesExtracted() throws IOException {
        Path currentDirectory = extractedTemplatesDirectory;
        if (currentDirectory != null && Files.exists(currentDirectory)) {
            return currentDirectory;
        }
        synchronized (this) {
            if (extractedTemplatesDirectory != null && Files.exists(extractedTemplatesDirectory)) {
                return extractedTemplatesDirectory;
            }
            Path classpathDirectory = locateTemplatesDirectory();
            if (classpathDirectory != null) {
                extractedTemplatesDirectory = classpathDirectory;
                templatesDirectoryIsTemporary = false;
                LOGGER.debug("ensureTemplatesExtracted() - Using Jasper templates directly from classpath directory {}", classpathDirectory);
                return classpathDirectory;
            }

            Path temporal = Files.createTempDirectory("bricodepot-jasper-templates");
            copyTemplatesFromClasspath(temporal, "*.jasper");
            copyTemplatesFromClasspath(temporal, "*.jrxml");
            copyTemplatesFromClasspath(temporal, "*.xml");
            extractedTemplatesDirectory = temporal;
            templatesDirectoryIsTemporary = true;
            LOGGER.debug("ensureTemplatesExtracted() - Copied Jasper templates from classpath to temporal directory {}", temporal);
            return temporal;
        }
    }

    private void copyTemplatesFromClasspath(Path destination, String pattern) throws IOException {
        Resource[] resources = resourceResolver.getResources("classpath*:" + CLASSPATH_TEMPLATE_DIRECTORY + pattern);
        for (Resource resource : resources) {
            if (!resource.exists()) {
                continue;
            }
            Path target = destination.resolve(Objects.requireNonNull(resource.getFilename()));
            try (InputStream input = resource.getInputStream()) {
                Files.copy(input, target, StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException exception) {
                throw new IOException("No se pudo copiar la plantilla " + resource.getFilename(), exception);
            }
        }
    }

    private Path locateTemplatesDirectory() {
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
            LOGGER.debug("locateTemplatesDirectory() - No fue posible resolver el directorio de plantillas del classpath", exception);
        }
        return null;
    }

    private String normaliseTemplateName(String templateName) {
        if (StringUtils.isBlank(templateName)) {
            return null;
        }
        String normalised = templateName.trim().replace(' ', '_');
        String lower = normalised.toLowerCase(Locale.ROOT);
        if (lower.endsWith(".jasper")) {
            normalised = normalised.substring(0, normalised.length() - ".jasper".length());
            lower = normalised.toLowerCase(Locale.ROOT);
        }
        else if (lower.endsWith(".jrxml")) {
            normalised = normalised.substring(0, normalised.length() - ".jrxml".length());
            lower = normalised.toLowerCase(Locale.ROOT);
        }
        normalised = normalised.replace('-', '_');
        if (TEMPLATE_ALIASES.containsKey(lower)) {
            return TEMPLATE_ALIASES.get(lower);
        }
        if (TEMPLATE_ALIASES.containsKey(normalised.toLowerCase(Locale.ROOT))) {
            return TEMPLATE_ALIASES.get(normalised.toLowerCase(Locale.ROOT));
        }
        return normalised;
    }

    private String safeToString(Object value) {
        return value == null ? null : value.toString();
    }
}
