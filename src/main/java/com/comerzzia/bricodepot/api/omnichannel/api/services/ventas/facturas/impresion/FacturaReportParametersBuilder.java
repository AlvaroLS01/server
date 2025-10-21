package com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas.impresion;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.core.model.actividades.ActividadBean;
import com.comerzzia.core.model.ventas.tickets.TicketBean;
import com.comerzzia.core.servicios.instancias.ServicioInstanciasImpl;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.core.util.xml.XMLDocument;
import com.comerzzia.core.util.xml.XMLDocumentNode;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.TicketVentaAbono;
import com.comerzzia.omnichannel.domain.dto.saledoc.SaleDocHdrDTO;
import com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument.OpcionesImpresionDocumentoVenta;

import net.sf.jasperreports.engine.JRParameter;

@Component
public class FacturaReportParametersBuilder {

    private static final Logger log = LoggerFactory.getLogger(FacturaReportParametersBuilder.class);

    private final TicketPaymentRefresher ticketPaymentRefresher;
    private final CardPaymentDataExtractor cardPaymentDataExtractor;
    private final PromotionExtractor promotionExtractor;
    private final FiscalDataExtractor fiscalDataExtractor;
    private final TicketLineAggregator ticketLineAggregator;

    @Autowired
    public FacturaReportParametersBuilder(TicketPaymentRefresher ticketPaymentRefresher,
            CardPaymentDataExtractor cardPaymentDataExtractor, PromotionExtractor promotionExtractor,
            FiscalDataExtractor fiscalDataExtractor, TicketLineAggregator ticketLineAggregator) {
        this.ticketPaymentRefresher = ticketPaymentRefresher;
        this.cardPaymentDataExtractor = cardPaymentDataExtractor;
        this.promotionExtractor = promotionExtractor;
        this.fiscalDataExtractor = fiscalDataExtractor;
        this.ticketLineAggregator = ticketLineAggregator;
    }

    public Map<String, Object> build(FacturaDocumento documento, IDatosSesion datosSesion,
            OpcionesImpresionDocumentoVenta opciones) {
        Map<String, Object> parametros = new HashMap<>(opciones.getParametrosPersonalizados());

        TicketBean ticketBean = documento.getTicket();
        TicketVentaAbono ticketVenta = documento.getTicketVenta();
        SaleDocHdrDTO saleDoc = documento.getSaleDocument();
        Locale locale = datosSesion.getLocale();

        parametros.put("ticket", ticketVenta);
        parametros.put("esDuplicado", opciones.isCopia());
        parametros.put("DEVOLUCION", Boolean.FALSE);
        parametros.put(JRParameter.REPORT_LOCALE, locale);
        parametros.put("UID_ACTIVIDAD", datosSesion.getUidActividad());

        if (ticketBean.getFecha() != null) {
            parametros.put("fecha", ticketBean.getFecha());
        }

        if (StringUtils.isNotBlank(ticketBean.getLocatorId())) {
            parametros.put("LOCATOR_ID", ticketBean.getLocatorId());
        }

        addTicketXmlData(ticketBean, parametros);
        addActividad(datosSesion, ticketBean, parametros);

        GiftCardSummary giftCardSummary = ticketPaymentRefresher.refreshPayments(ticketVenta, saleDoc);
        parametros.put("pagoGiftcard", giftCardSummary.getTotalPayment());

        CardPaymentDataExtractor.CardPaymentData cardData = cardPaymentDataExtractor.extract(ticketBean);
        parametros.put("listaPagosTarjetaDatosPeticion",
                cardData.getPeticiones().isEmpty() ? null : cardData.getPeticiones());
        parametros.put("listaPagosTarjeta", cardData.getRespuestas().isEmpty() ? null : cardData.getRespuestas());

        List<FacturaPromocion> promociones = promotionExtractor.extract(ticketBean);
        parametros.put("listaPromociones", promociones.isEmpty() ? null : promociones);
        parametros.put("lineasAgrupadas", ticketLineAggregator.aggregate(ticketVenta));

        fiscalDataExtractor.appendFiscalData(ticketBean, parametros);

        return parametros;
    }

    private void addTicketXmlData(TicketBean ticketBean, Map<String, Object> parametros) {
        try {
            if (ticketBean.getXml() == null) {
                return;
            }

            String fechaOrigen = obtenerFechaOrigen(ticketBean.getXml());
            if (fechaOrigen != null) {
                parametros.put("fecha_origen", fechaOrigen);
            }

            String numPedido = obtenerNumeroPedido(ticketBean.getXml());
            if (numPedido != null) {
                parametros.put("numPedido", numPedido);
            }
        }
        catch (Exception e) {
            log.debug("FacturaReportParametersBuilder.addTicketXmlData() - No se pudo leer información adicional del ticket", e);
        }
    }

    private void addActividad(IDatosSesion datosSesion, TicketBean ticketBean, Map<String, Object> parametros) {
        try {
            ActividadBean actividad = ServicioInstanciasImpl.get().consultarActividad(ticketBean.getUidActividad());
            if (actividad != null) {
                parametros.put("UID_INSTANCIA", actividad.getUidInstancia());
            }
        }
        catch (Exception e) {
            log.warn("FacturaReportParametersBuilder.addActividad() - No se pudo obtener la actividad {}", ticketBean.getUidActividad(), e);
        }
    }

    private String obtenerFechaOrigen(org.w3c.dom.Document document) {
        if (document != null && document.getElementsByTagName("fechaTicketOrigen") != null
                && document.getElementsByTagName("fechaTicketOrigen").item(0) != null) {
            return document.getElementsByTagName("fechaTicketOrigen").item(0).getTextContent();
        }
        return null;
    }

    private String obtenerNumeroPedido(org.w3c.dom.Document document) {
        XMLDocument xmlDocument = new XMLDocument(document);
        XMLDocumentNode pagosNode = xmlDocument.getNodo("pagos", true);

        if (pagosNode == null) {
            return null;
        }

        for (XMLDocumentNode pagoNode : pagosNode.getHijos("pago")) {
            XMLDocumentNode extendedDataNode = pagoNode.getNodo("extendedData", true);
            if (extendedDataNode != null) {
                XMLDocumentNode documentoNode = extendedDataNode.getNodo("documento", true);
                if (documentoNode != null) {
                    return documentoNode.getValue();
                }
            }
        }
        return null;
    }
}
