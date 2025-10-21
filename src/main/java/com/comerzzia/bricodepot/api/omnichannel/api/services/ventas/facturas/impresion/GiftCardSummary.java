package com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas.impresion;

import java.math.BigDecimal;
import java.util.List;
import java.util.StringJoiner;

import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.PagoTicket;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.TarjetaRegaloTicket;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.TicketVentaAbono;

/**
 * Información agregada de los pagos realizados con tarjetas regalo.
 */
public class GiftCardSummary {

    private final String cardNumbers;
    private final String transactionId;
    private final BigDecimal balance;
    private final BigDecimal totalPayment;
    private final BigDecimal totalReload;

    private GiftCardSummary(String cardNumbers, String transactionId, BigDecimal balance, BigDecimal totalPayment,
            BigDecimal totalReload) {
        this.cardNumbers = cardNumbers;
        this.transactionId = transactionId;
        this.balance = balance;
        this.totalPayment = totalPayment;
        this.totalReload = totalReload;
    }

    public static GiftCardSummary fromTicket(TicketVentaAbono ticketVenta) {
        if (ticketVenta == null || ticketVenta.getPagos() == null || ticketVenta.getPagos().isEmpty()) {
            return empty();
        }

        StringJoiner numbers = new StringJoiner("/");
        String transaction = null;
        BigDecimal balance = BigDecimal.ZERO;
        BigDecimal totalPayment = BigDecimal.ZERO;
        BigDecimal totalReload = BigDecimal.ZERO;

        List<PagoTicket> pagos = ticketVenta.getPagos();
        for (PagoTicket pago : pagos) {
            if (pago.getGiftcards() != null && !pago.getGiftcards().isEmpty()) {
                TarjetaRegaloTicket tarjeta = pago.getGiftcards().get(0);

                if (tarjeta.getNumTarjetaRegalo() != null) {
                    numbers.add(tarjeta.getNumTarjetaRegalo());
                }
                if (transaction == null || transaction.isEmpty()) {
                    transaction = tarjeta.getUidTransaccion();
                }
                if (tarjeta.getSaldo() != null) {
                    balance = tarjeta.getSaldo();
                }
                if (tarjeta.getImportePago() != null) {
                    totalPayment = totalPayment.add(tarjeta.getImportePago());
                }
                if (tarjeta.getImporteRecarga() != null) {
                    totalReload = totalReload.add(tarjeta.getImporteRecarga());
                }
            }
        }

        String numbersValue = numbers.length() > 0 ? numbers.toString() : null;
        return new GiftCardSummary(numbersValue, transaction, balance, totalPayment, totalReload);
    }

    public static GiftCardSummary empty() {
        return new GiftCardSummary(null, null, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public boolean hasData() {
        return cardNumbers != null && !cardNumbers.isEmpty();
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void applyTo(TicketVentaAbono ticketVenta) {
        if (ticketVenta == null || ticketVenta.getCabecera() == null) {
            return;
        }

        if (!hasData()) {
            ticketVenta.getCabecera().setTarjetaRegalo(null);
            return;
        }

        TarjetaRegaloTicket tarjetaRegalo = ticketVenta.getCabecera().getTarjetaRegalo();
        if (tarjetaRegalo == null) {
            tarjetaRegalo = new TarjetaRegaloTicket();
        }

        tarjetaRegalo.setNumTarjetaRegalo(cardNumbers);
        tarjetaRegalo.setUidTransaccion(transactionId);
        tarjetaRegalo.setSaldo(balance);
        tarjetaRegalo.setImporteRecarga(totalReload);

        ticketVenta.getCabecera().setTarjetaRegalo(tarjetaRegalo);
    }
}
