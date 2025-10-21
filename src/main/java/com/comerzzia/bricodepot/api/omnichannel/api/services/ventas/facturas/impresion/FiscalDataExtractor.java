package com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas.impresion;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.comerzzia.core.model.ventas.tickets.TicketBean;
import com.comerzzia.core.util.base64.Base64Coder;
import com.comerzzia.core.util.xml.XMLDocumentUtils;

@Component
public class FiscalDataExtractor {

    private static final Logger log = LoggerFactory.getLogger(FiscalDataExtractor.class);

    private final QrCodeGenerator qrCodeGenerator;

    @Autowired
    public FiscalDataExtractor(QrCodeGenerator qrCodeGenerator) {
        this.qrCodeGenerator = qrCodeGenerator;
    }

    public void appendFiscalData(TicketBean ticketBean, Map<String, Object> parametros) {
        if (ticketBean == null || ticketBean.getXml() == null) {
            return;
        }

        try {
            Document doc = ticketBean.getXml();
            Element root = doc.getDocumentElement();
            Element cabecera = XMLDocumentUtils.getElement(root, "cabecera", false);
            Element fiscalData = XMLDocumentUtils.getElement(cabecera, "fiscal_data", true);

            if (fiscalData == null) {
                return;
            }

            List<Element> listaFiscalData = XMLDocumentUtils.getChildElements(fiscalData);
            for (Element element : listaFiscalData) {
                String name = XMLDocumentUtils.getTagValueAsString(element, "name", true);
                String value = XMLDocumentUtils.getTagValueAsString(element, "value", true);

                if ("QR".equals(name)) {
                    parametros.put("fiscalData_QR", value);
                    try {
                        parametros.put("QR_PORTUGAL", createQrStream(value));
                    }
                    catch (Exception e) {
                        log.warn("FiscalDataExtractor.appendFiscalData() - No se pudo generar el QR fiscal", e);
                    }
                }
                else if ("ATCUD".equals(name)) {
                    parametros.put("fiscalData_ACTUD", value);
                }
            }
        }
        catch (Exception e) {
            log.debug("FiscalDataExtractor.appendFiscalData() - No se pudo leer la información fiscal", e);
        }
    }

    private InputStream createQrStream(String base64Value) throws Exception {
        Base64Coder coder = new Base64Coder(Base64Coder.UTF8);
        String qrText = coder.decodeBase64(base64Value);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(qrCodeGenerator.generate(qrText), "jpeg", outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
