package com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas.impresion;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoBean;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.TicketVentaAbono;

@Component
public class FacturaTemplateResolver {

    public String resolve(TicketVentaAbono ticketVenta, TipoDocumentoBean tipoDocumento, Map<String, Object> parametros) {
        boolean devolucion = ticketVenta != null && ticketVenta.getCabecera() != null
                && "FR".equals(ticketVenta.getCabecera().getCodTipoDocumento());
        parametros.put("DEVOLUCION", devolucion);

        String codPais = tipoDocumento != null ? tipoDocumento.getCodPais() : null;
        if ("PT".equalsIgnoreCase(codPais)) {
            return devolucion ? "facturaDevolucionA4_PT.jasper" : "facturaA4_PT.jasper";
        }
        if ("CA".equalsIgnoreCase(codPais)) {
            return "facturaA4_CA.jasper";
        }
        return "facturaA4.jasper";
    }
}
