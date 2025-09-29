package com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.executor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.ReportProperties;
import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.model.TrabajoInformeBean;
import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.model.TrabajoInformeBean.ParametrosTrabajo;

/**
 * Simplified PDF renderer used in absence of the legacy Jasper engine. It produces a
 * readable PDF summarising the information passed in the {@link TrabajoInformeBean}.
 */
public class PdfBoxReportRenderer implements ReportRenderer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfBoxReportRenderer.class);

    private final ReportProperties properties;

    public PdfBoxReportRenderer(ReportProperties properties) {
        this.properties = properties;
    }

    @Override
    public byte[] render(TrabajoInformeBean trabajoInformeBean) {
        try (PDDocument document = new PDDocument();
                ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(50, 780);
                contentStream.showText("Factura - " + trabajoInformeBean.getInforme());

                contentStream.setFont(PDType1Font.HELVETICA, 11);
                contentStream.newLineAtOffset(0, -30);

                ParametrosTrabajo parametros = trabajoInformeBean.getParametros();
                writeLine(contentStream, "Report version: " + trabajoInformeBean.getReportVersion());
                writeLine(contentStream, "Base path: " + properties.getBasePath());
                writeLine(contentStream, "Subreport dir: " + parametros.getSubreportDir());
                writeLine(contentStream, "Duplicado: " + parametros.isDuplicado());
                if (parametros.getFechaTicket() != null) {
                    writeLine(contentStream, "Fecha ticket: "
                            + parametros.getFechaTicket().format(DateTimeFormatter.ISO_DATE_TIME));
                }
                writeLine(contentStream, "Ticket: " + parametros.getCodigoTicket());
                writeLine(contentStream, "País: " + parametros.getPais());
                for (Map.Entry<String, Object> entry : parametros.getParametrosAdicionales().entrySet()) {
                    writeLine(contentStream, entry.getKey() + ": " + String.valueOf(entry.getValue()));
                }
                contentStream.endText();
            }

            document.save(out);
            return out.toByteArray();
        } catch (IOException e) {
            LOGGER.error("Error generating PDF", e);
            throw new ReportRendererException("Unable to generate PDF", e);
        }
    }

    private void writeLine(PDPageContentStream contentStream, String value) throws IOException {
        contentStream.showText(value);
        contentStream.newLineAtOffset(0, -16);
    }
}

