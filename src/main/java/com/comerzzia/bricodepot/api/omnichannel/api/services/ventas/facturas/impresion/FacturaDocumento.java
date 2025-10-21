package com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas.impresion;

import com.comerzzia.core.model.ventas.tickets.TicketBean;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.TicketVentaAbono;
import com.comerzzia.omnichannel.domain.dto.saledoc.SaleDocHdrDTO;

/**
 * Agrupa toda la información necesaria para preparar la impresión de una factura.
 */
public class FacturaDocumento {

    private final TicketBean ticket;
    private final TicketVentaAbono ticketVenta;
    private final SaleDocHdrDTO saleDocument;

    public FacturaDocumento(TicketBean ticket, TicketVentaAbono ticketVenta, SaleDocHdrDTO saleDocument) {
        this.ticket = ticket;
        this.ticketVenta = ticketVenta;
        this.saleDocument = saleDocument;
    }

    public TicketBean getTicket() {
        return ticket;
    }

    public TicketVentaAbono getTicketVenta() {
        return ticketVenta;
    }

    public SaleDocHdrDTO getSaleDocument() {
        return saleDocument;
    }
}
