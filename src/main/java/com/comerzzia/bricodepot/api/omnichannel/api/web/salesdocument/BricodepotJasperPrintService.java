package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
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

import com.comerzzia.core.servicios.api.errorhandlers.ApiException;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.LineaTicket;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.TicketVentaAbono;


import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

@Service
@Primary
public class BricodepotJasperPrintService extends JasperPrintServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(BricodepotJasperPrintService.class);

    private static final String CLASSPATH_TEMPLATE_DIRECTORY = "informes/ventas/facturas/";

    private static final String LEGACY_TICKET_CLASS =
            "com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.TicketVentaAbono";
    private static final String LEGACY_TICKET_CLASS_RESOURCE = LEGACY_TICKET_CLASS.replace('.', '/');

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

        registerPropertyAliases();
    }

    private final PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
    private final Map<String, File> templateCache = new ConcurrentHashMap<>();
    private volatile Path extractedTemplatesDirectory;
    private volatile boolean templatesDirectoryIsTemporary;

    @Override
    protected Map<String, Object> generateDocParameters(IDatosSesion datosSesion, PrintDocumentDTO printRequest)
            throws ApiException {
        Map<String, Object> docParameters = super.generateDocParameters(datosSesion, printRequest);
        normaliseTicketBreakdowns(docParameters);
        return docParameters;
    }

    @Override
    protected JasperPrint getJasperPrint(IDatosSesion datosSesion, PrintDocumentDTO printRequest) throws ApiException {
        LOGGER.debug("getJasperPrint() - Generando documento con parametros: {}", printRequest);

        Map<String, Object> docParameters = generateDocParameters(datosSesion, printRequest);
        File templateFile = (File) docParameters.get(DocumentPrintService.TEMPLATE_FILE);

        try {
            return fillReport(templateFile, docParameters);
        }
        catch (JRException exception) {
            if (requiresLegacyTicketClassFix(exception) && recompileTemplate(templateFile)) {
                try {
                    return fillReport(templateFile, docParameters);
                }
                catch (JRException retryException) {
                    exception = retryException;
                }
            }

            String message = String.format("Error al generar el informe Jasper '%s': %s", templateFile,
                    exception.getMessage());
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
        candidates.add(template + ".jrxml");
        return candidates;
    }

    private File resolveTemplate(String fileName) throws ApiException {
        try {
            Path directory = ensureTemplatesExtracted();
            Path templatePath = directory.resolve(fileName);
            if (Files.exists(templatePath)) {
                if (fileName.toLowerCase(Locale.ROOT).endsWith(".jrxml")) {
                    File compiled = compileTemplate(templatePath);
                    if (compiled != null) {
                        return compiled;
                    }
                    return null;
                }
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

    private JasperPrint fillReport(File templateFile, Map<String, Object> docParameters) throws JRException {
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(templateFile);
        jasperReport.setProperty(JRPropertiesUtil.PROPERTY_PREFIX + "javabean.use.field.description",
                Boolean.TRUE.toString());
        return JasperFillManager.fillReport(jasperReport, docParameters, new JREmptyDataSource());
    }

    private void normaliseTicketBreakdowns(Map<String, Object> docParameters) {
        if (docParameters == null || docParameters.isEmpty()) {
            return;
        }
        sanitizeTicket(docParameters.get("ticket"));
        sanitizeTicket(docParameters.get("document"));
        sanitizeLineCollection(docParameters.get("lineas"));
        sanitizeLineCollection(docParameters.get("lineasAgrupadas"));
    }

    private void sanitizeTicket(Object candidate) {
        if (!(candidate instanceof TicketVentaAbono)) {
            return;
        }
        TicketVentaAbono ticket = (TicketVentaAbono) candidate;
        List<LineaTicket> lines = ticket.getLineas();
        if (lines != null) {
            lines.forEach(this::sanitizeBreakdownValue);
        }
    }

    @SuppressWarnings("unchecked")
    private void sanitizeLineCollection(Object candidate) {
        if (!(candidate instanceof List<?>)) {
            return;
        }
        List<?> values = (List<?>) candidate;
        if (values.isEmpty()) {
            return;
        }
        for (Object value : values) {
            if (value instanceof LineaTicket) {
                sanitizeBreakdownValue((LineaTicket) value);
            }
        }
    }

    private void sanitizeBreakdownValue(LineaTicket line) {
        if (line == null) {
            return;
        }
        String desglose2 = line.getDesglose2();
        if (StringUtils.isBlank(desglose2)) {
            return;
        }

        String trimmed = desglose2.trim();
        if ("*".equals(trimmed)) {
            line.setDesglose2("");
            return;
        }

        String normalised = trimmed.replace(',', '.');
        try {
            new BigDecimal(normalised);
        }
        catch (NumberFormatException exception) {
            line.setDesglose2("");
        }
    }

    private static boolean registerPropertyAliases() {
        try {
            BeanUtilsBean beanUtils = BeanUtilsBean.getInstance();
            PropertyUtilsBean propertyUtils = beanUtils.getPropertyUtils();
            AliasAwarePropertyUtilsBean aliasAware = propertyUtils instanceof AliasAwarePropertyUtilsBean
                    ? (AliasAwarePropertyUtilsBean) propertyUtils
                    : new AliasAwarePropertyUtilsBean();

            aliasAware.addAlias("codImp", "codImpuesto");
            aliasAware.addAlias("medioPago.desMedioPago", "desMedioPago");

            if (propertyUtils != aliasAware) {
                BeanUtilsBean.setInstance(new BeanUtilsBean(beanUtils.getConvertUtils(), aliasAware));
            }
            return true;
        }
        catch (Exception exception) {
            LoggerFactory.getLogger(BricodepotJasperPrintService.class)
                    .warn("registerPropertyAliases() - No fue posible registrar los alias de propiedades", exception);
            return false;
        }
    }

    private static final class AliasAwarePropertyUtilsBean extends PropertyUtilsBean {

        private final Map<String, String> aliases = new ConcurrentHashMap<>();

        void addAlias(String alias, String targetProperty) {
            if (StringUtils.isAnyBlank(alias, targetProperty)) {
                return;
            }
            aliases.put(alias.trim(), targetProperty.trim());
        }

        private String resolveAlias(String name) {
            if (name == null) {
                return null;
            }
            String trimmed = name.trim();
            String alias = aliases.get(trimmed);
            return alias != null ? alias : trimmed;
        }

        @Override
        public Object getProperty(Object bean, String name)
                throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            return super.getProperty(bean, resolveAlias(name));
        }

        @Override
        public void setProperty(Object bean, String name, Object value)
                throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            super.setProperty(bean, resolveAlias(name), value);
        }

        @Override
        public Object getNestedProperty(Object bean, String name)
                throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            return super.getNestedProperty(bean, resolveAlias(name));
        }

        @Override
        public Object getSimpleProperty(Object bean, String name)
                throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            return super.getSimpleProperty(bean, resolveAlias(name));
        }

        @Override
        public Object getIndexedProperty(Object bean, String name)
                throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            return super.getIndexedProperty(bean, resolveAlias(name));
        }

        @Override
        public Object getMappedProperty(Object bean, String name)
                throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            return super.getMappedProperty(bean, resolveAlias(name));
        }

        @Override
        public PropertyDescriptor getPropertyDescriptor(Object bean, String name)
                throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            return super.getPropertyDescriptor(bean, resolveAlias(name));
        }

        @Override
        public Class<?> getPropertyType(Object bean, String name)
                throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            return super.getPropertyType(bean, resolveAlias(name));
        }

        @Override
        public boolean isReadable(Object bean, String name) {
            return super.isReadable(bean, resolveAlias(name));
        }

        @Override
        public boolean isWriteable(Object bean, String name) {
            return super.isWriteable(bean, resolveAlias(name));
        }
    }

    private boolean requiresLegacyTicketClassFix(Throwable throwable) {
        Throwable current = throwable;
        while (current != null) {
            String message = current.getMessage();
            if (message != null && (message.contains(LEGACY_TICKET_CLASS) || message.contains(LEGACY_TICKET_CLASS_RESOURCE))) {
                return true;
            }
            if (current instanceof NoClassDefFoundError) {
                String errorMessage = ((NoClassDefFoundError) current).getMessage();
                if (errorMessage != null && errorMessage.contains(LEGACY_TICKET_CLASS_RESOURCE)) {
                    return true;
                }
            }
            current = current.getCause();
        }
        return false;
    }

    private boolean recompileTemplate(File templateFile) {
        String lowerName = templateFile.getName().toLowerCase(Locale.ROOT);
        if (!lowerName.endsWith(".jasper")) {
            return false;
        }

        Path templatePath = templateFile.toPath();
        String sourceName = templateFile.getName().substring(0, templateFile.getName().length() - ".jasper".length())
                + ".jrxml";
        Path sourcePath = templatePath.resolveSibling(sourceName);
        if (!Files.exists(sourcePath)) {
            LOGGER.warn("recompileTemplate() - No se encontró el fichero JRXML {} para recompilar {}", sourcePath, templatePath);
            return false;
        }

        try {
            JasperCompileManager.compileReportToFile(sourcePath.toString(), templatePath.toString());
            LOGGER.info("recompileTemplate() - Recompilada la plantilla Jasper {} a partir de {}", templatePath, sourcePath);
            return true;
        }
        catch (JRException exception) {
            LOGGER.error("recompileTemplate() - Falló la recompilación de {}", templatePath, exception);
            return false;
        }
    }

    private File compileTemplate(Path jrxmlPath) throws ApiException {
        String fileName = jrxmlPath.getFileName().toString();
        Path jasperPath = jrxmlPath.resolveSibling(fileName.substring(0, fileName.length() - ".jrxml".length()) + ".jasper");
        try {
            JasperCompileManager.compileReportToFile(jrxmlPath.toString(), jasperPath.toString());
            LOGGER.debug("compileTemplate() - Compilada la plantilla {} a partir de {}", jasperPath, jrxmlPath);
            return templateCache.computeIfAbsent(jasperPath.getFileName().toString(), key -> {
                File file = jasperPath.toFile();
                if (templatesDirectoryIsTemporary) {
                    file.deleteOnExit();
                }
                return file;
            });
        }
        catch (JRException exception) {
            throw new DocumentoVentaImpresionException("No se pudo compilar la plantilla " + fileName, exception);
        }
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