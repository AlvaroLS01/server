package com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas.impresion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Component
public class JasperReportRenderer {

    private static final String REPORTS_PATH = "classpath:informes/ventas/facturas/";

    private final PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    public byte[] render(String templateName, Map<String, Object> parametros, Locale locale) throws IOException, JRException {
        parametros.put(JRParameter.REPORT_LOCALE, locale);
        Path tempDir = Files.createTempDirectory("facturaA4");

        try {
            copiarSubinformes(tempDir);
            parametros.put("SUBREPORT_DIR", tempDir.toAbsolutePath().toString() + java.io.File.separator);

            Resource mainTemplate = resourceResolver.getResource(REPORTS_PATH + templateName);
            if (!mainTemplate.exists()) {
                throw new IOException("No se encontró la plantilla de impresión: " + templateName);
            }

            try (InputStream in = mainTemplate.getInputStream(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(in);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
                exporter.exportReport();

                return out.toByteArray();
            }
        }
        finally {
            FileSystemUtils.deleteRecursively(tempDir);
        }
    }

    private void copiarSubinformes(Path tempDir) throws IOException {
        Resource[] recursos = resourceResolver.getResources("classpath*:informes/ventas/facturas/*.jasper");
        for (Resource recurso : recursos) {
            try (InputStream inputStream = recurso.getInputStream()) {
                Files.copy(inputStream, tempDir.resolve(recurso.getFilename()), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}
