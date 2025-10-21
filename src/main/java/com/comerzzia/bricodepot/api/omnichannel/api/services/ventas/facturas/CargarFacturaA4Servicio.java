package com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.core.util.xml.XMLDocumentException;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.LineaTicket;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.TicketVentaAbono;
import com.comerzzia.core.model.ventas.tickets.TicketBean;

public interface CargarFacturaA4Servicio {

        String getSubReportDir(String fileName);

        void generarMediosPago(TicketVentaAbono ticketVentaAbono, IDatosSesion datosSesion) throws Exception;

        void addFiscalData(TicketBean ticketBean, Map<String, Object> parametros) throws Exception;

        BufferedImage generateQRCodeImage(String barcodeText) throws Exception;

        void cargarDatosPagoTarjeta(TicketBean ticket, TicketVentaAbono ticketVentaAbono, Map<String, Object> parametros);

        void cargarPromociones(TicketBean ticket, Map<String, Object> parametros) throws XMLDocumentException;

        List<LineaTicket> getLineasAgrupadas(TicketVentaAbono ticketVenta);

        void getPagoGiftCard(TicketVentaAbono ticketVenta, Map<String, Object> parametros);
}
