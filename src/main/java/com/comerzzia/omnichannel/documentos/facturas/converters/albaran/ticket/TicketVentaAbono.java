package com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

/**
 * Adaptador que replica el tipo legacy {@code TicketVentaAbono} esperado por las
 * plantillas Jasper de facturas. Extiende el modelo actual y expone un método de
 * factoría para clonar la información recibida desde la API, de forma que el
 * informe puede seguir realizando el cast a la clase del paquete legacy sin
 * modificar las plantillas existentes.
 */
public class TicketVentaAbono
        extends com.comerzzia.omnichannel.model.documents.sales.ticket.TicketVentaAbono {

    private static final long serialVersionUID = 1L;

    public TicketVentaAbono() {
        super();
    }

    public static TicketVentaAbono fromModel(
            com.comerzzia.omnichannel.model.documents.sales.ticket.TicketVentaAbono source) {
        if (source == null) {
            return null;
        }
        if (source instanceof TicketVentaAbono) {
            return (TicketVentaAbono) source;
        }
        TicketVentaAbono adapted = new TicketVentaAbono();
        copyProperties(source, adapted);
        return adapted;
    }

    private static void copyProperties(
            com.comerzzia.omnichannel.model.documents.sales.ticket.TicketVentaAbono source,
            TicketVentaAbono target) {
        try {
            BeanUtils.copyProperties(source, target);
        }
        catch (BeansException exception) {
            throw new IllegalStateException("No se pudo adaptar el ticket de venta al formato legacy", exception);
        }
    }
}
