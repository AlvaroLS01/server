package com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas.impresion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.comerzzia.core.model.ventas.tickets.TicketBean;
import com.comerzzia.core.util.xml.XMLDocumentUtils;
import com.comerzzia.pos.services.ticket.pagos.tarjeta.DatosPeticionPagoTarjeta;
import com.comerzzia.pos.services.ticket.pagos.tarjeta.DatosRespuestaPagoTarjeta;

@Component
public class CardPaymentDataExtractor {

    private static final Logger log = LoggerFactory.getLogger(CardPaymentDataExtractor.class);

    public CardPaymentData extract(TicketBean ticketBean) {
        if (ticketBean == null || ticketBean.getXml() == null) {
            return CardPaymentData.empty();
        }

        List<DatosPeticionPagoTarjeta> peticiones = new ArrayList<>();
        List<DatosRespuestaPagoTarjeta> respuestas = new ArrayList<>();

        try {
            Document document = ticketBean.getXml();
            Element root = document.getDocumentElement();
            Element pagos = XMLDocumentUtils.getElement(root, "pagos", false);

            if (pagos == null) {
                return CardPaymentData.empty();
            }

            List<Element> listaPagos = XMLDocumentUtils.getChildElements(pagos);
            for (Element pago : listaPagos) {
                String codigoMedioPago = XMLDocumentUtils.getTagValue(pago, "codmedpag", false);
                if (!"0010".equals(codigoMedioPago)) {
                    continue;
                }

                DatosPeticionPagoTarjeta peticion = new DatosPeticionPagoTarjeta();
                DatosRespuestaPagoTarjeta respuesta = new DatosRespuestaPagoTarjeta();

                Element datosRespuestaPagoTarjeta = XMLDocumentUtils.getElement(pago, "datosRespuestaPagoTarjeta", true);
                if (datosRespuestaPagoTarjeta != null) {
                    Element datosPeticion = XMLDocumentUtils.getElement(datosRespuestaPagoTarjeta, "datosPeticion", true);
                    if (datosPeticion != null) {
                        peticion.setIdDocumento(XMLDocumentUtils.getTagValueAsLong(datosPeticion, "idDocumento", true));
                        peticion.setIdTransaccion(XMLDocumentUtils.getTagValueAsString(datosPeticion, "idTransaccion", true));
                        Element documentoOrigen = XMLDocumentUtils.getElement(datosPeticion, "documentoOrigen", true);
                        if (documentoOrigen != null) {
                            peticion.setIdDocumentoOrigen(documentoOrigen.getTextContent());
                        }
                    }

                    Element cabecera = XMLDocumentUtils.getElement(root, "cabecera", true);
                    if (cabecera != null) {
                        peticion.setFechaDocumentoOrigen(XMLDocumentUtils.getTagValueAsString(cabecera, "fecha", true));
                    }

                    Element datosRespuesta = XMLDocumentUtils.getElement(datosRespuestaPagoTarjeta, "datosRespuesta", true);
                    if (datosRespuesta != null) {
                        Element tarjeta = XMLDocumentUtils.getElement(datosRespuesta, "tarjeta", true);
                        if (tarjeta != null) {
                            respuesta.setTarjeta(tarjeta.getTextContent());
                        }
                        Element aid = XMLDocumentUtils.getElement(datosRespuesta, "AID", true);
                        if (aid != null) {
                            respuesta.setAID(aid.getTextContent());
                        }
                        Element tipoLectura = XMLDocumentUtils.getElement(datosRespuesta, "tipoLectura", true);
                        if (tipoLectura != null) {
                            respuesta.setTipoLectura(tipoLectura.getTextContent());
                        }
                        Element terminal = XMLDocumentUtils.getElement(datosRespuesta, "terminal", true);
                        if (terminal != null) {
                            respuesta.setTerminal(terminal.getTextContent());
                        }
                        Element comercio = XMLDocumentUtils.getElement(datosRespuesta, "comercio", true);
                        if (comercio != null) {
                            respuesta.setComercio(comercio.getTextContent());
                        }
                        Element codAutorizacion = XMLDocumentUtils.getElement(datosRespuesta, "codAutorizacion", true);
                        if (codAutorizacion != null) {
                            respuesta.setCodAutorizacion(codAutorizacion.getTextContent());
                        }
                        Element arc = XMLDocumentUtils.getElement(datosRespuesta, "ARC", true);
                        if (arc != null) {
                            respuesta.setARC(arc.getTextContent());
                        }
                        Element applicationLabel = XMLDocumentUtils.getElement(datosRespuesta, "applicationLabel", true);
                        if (applicationLabel != null) {
                            respuesta.setApplicationLabel(applicationLabel.getTextContent());
                        }
                        Element marcaTarjeta = XMLDocumentUtils.getElement(datosRespuesta, "marcaTarjeta", true);
                        if (marcaTarjeta != null) {
                            respuesta.setMarcaTarjeta(marcaTarjeta.getTextContent());
                        }
                    }
                }

                peticiones.add(peticion);
                respuestas.add(respuesta);
            }
        }
        catch (Exception e) {
            log.warn("CardPaymentDataExtractor.extract() - Error obteniendo datos de pago con tarjeta", e);
        }

        if (peticiones.isEmpty() && respuestas.isEmpty()) {
            return CardPaymentData.empty();
        }

        return new CardPaymentData(peticiones, respuestas);
    }

    public static final class CardPaymentData {
        private final List<DatosPeticionPagoTarjeta> peticiones;
        private final List<DatosRespuestaPagoTarjeta> respuestas;

        private CardPaymentData(List<DatosPeticionPagoTarjeta> peticiones, List<DatosRespuestaPagoTarjeta> respuestas) {
            this.peticiones = peticiones;
            this.respuestas = respuestas;
        }

        public static CardPaymentData empty() {
            return new CardPaymentData(Collections.emptyList(), Collections.emptyList());
        }

        public List<DatosPeticionPagoTarjeta> getPeticiones() {
            return peticiones;
        }

        public List<DatosRespuestaPagoTarjeta> getRespuestas() {
            return respuestas;
        }
    }
}
