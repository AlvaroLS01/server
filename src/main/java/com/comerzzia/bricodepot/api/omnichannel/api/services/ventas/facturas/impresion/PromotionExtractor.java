package com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas.impresion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.comerzzia.core.model.ventas.tickets.TicketBean;
import com.comerzzia.core.util.xml.XMLDocumentException;
import com.comerzzia.core.util.xml.XMLDocumentUtils;

@Component
public class PromotionExtractor {

    private static final Logger log = LoggerFactory.getLogger(PromotionExtractor.class);

    public List<FacturaPromocion> extract(TicketBean ticketBean) {
        List<FacturaPromocion> result = new ArrayList<>();

        if (ticketBean == null || ticketBean.getXml() == null) {
            return result;
        }

        try {
            Document document = ticketBean.getXml();
            Element root = document.getDocumentElement();
            Element promociones = XMLDocumentUtils.getElement(root, "promociones", true);

            if (promociones == null) {
                return result;
            }

            List<Element> listaPromocion = XMLDocumentUtils.getChildElements(promociones);
            for (Element promo : listaPromocion) {
                FacturaPromocion data = new FacturaPromocion();
                data.setIdPromocion(XMLDocumentUtils.getTagValueAsLong(promo, "idpromocion", false));

                try {
                    data.setTextoPromocion(XMLDocumentUtils.getTagValueAsString(promo, "texto_promocion", false));
                }
                catch (Exception e) {
                    log.debug("PromotionExtractor.extract() - Texto de promoción no disponible: {}", e.getMessage());
                    data.setTextoPromocion("");
                }

                double importeTotalAhorro = XMLDocumentUtils.getTagValueAsDouble(promo, "importe_total_ahorro", false);
                data.setImporteTotalAhorro(BigDecimal.valueOf(importeTotalAhorro));

                result.add(data);
            }
        }
        catch (XMLDocumentException e) {
            log.warn("PromotionExtractor.extract() - Error leyendo las promociones del ticket {}", ticketBean.getUidTicket(), e);
        }

        return result;
    }
}
