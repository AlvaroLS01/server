package com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas.impresion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.PagoTicket;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.TicketVentaAbono;
import com.comerzzia.omnichannel.domain.dto.saledoc.SaleDocHdrDTO;
import com.comerzzia.omnichannel.domain.dto.saledoc.SaleDocPaymentDTO;

@Component
public class TicketPaymentRefresher {

    public GiftCardSummary refreshPayments(TicketVentaAbono ticketVenta, SaleDocHdrDTO saleDoc) {
        GiftCardSummary summary = GiftCardSummary.fromTicket(ticketVenta);

        if (saleDoc != null && saleDoc.getPayments() != null && !saleDoc.getPayments().isEmpty()) {
            List<PagoTicket> pagosActualizados = new ArrayList<>();
            for (SaleDocPaymentDTO payment : saleDoc.getPayments()) {
                PagoTicket pagoTicket = new PagoTicket();
                pagoTicket.getMedioPago().setCodMedioPago(payment.getPaymentMethodCode());
                pagoTicket.getMedioPago().setDesMedioPago(payment.getPaymentMethodDes());
                pagoTicket.setImporte(payment.getGrossAmount());
                pagosActualizados.add(pagoTicket);
            }

            ticketVenta.getPagos().clear();
            ticketVenta.getPagos().addAll(pagosActualizados);
        }

        summary.applyTo(ticketVenta);

        return summary;
    }
}
