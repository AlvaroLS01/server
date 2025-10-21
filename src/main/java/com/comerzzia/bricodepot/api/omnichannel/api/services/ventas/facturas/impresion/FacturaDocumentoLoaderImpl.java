package com.comerzzia.bricodepot.api.omnichannel.api.services.ventas.facturas.impresion;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.aena.util.xml.MarshallUtil;
import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.core.model.ventas.tickets.TicketBean;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.core.servicios.ventas.tickets.TicketService;
import com.comerzzia.omnichannel.documentos.facturas.converters.albaran.ticket.TicketVentaAbono;
import com.comerzzia.omnichannel.domain.dto.saledoc.SaleDocHdrDTO;
import com.comerzzia.omnichannel.service.salesdocument.SaleDocumentService;

@Component
public class FacturaDocumentoLoaderImpl implements FacturaDocumentoLoader {

    private static final Logger log = LoggerFactory.getLogger(FacturaDocumentoLoaderImpl.class);

    private final TicketService ticketService;
    private final SaleDocumentService saleDocumentService;

    @Autowired
    public FacturaDocumentoLoaderImpl(TicketService ticketService, SaleDocumentService saleDocumentService) {
        this.ticketService = ticketService;
        this.saleDocumentService = saleDocumentService;
    }

    @Override
    public Optional<FacturaDocumento> load(String documentUid, IDatosSesion datosSesion) {
        try {
            TicketBean ticketBean = ticketService.consultarTicketUid(datosSesion, documentUid);

            if (ticketBean == null) {
                log.warn("FacturaDocumentoLoaderImpl.load() - No se encontró el ticket {}", documentUid);
                return Optional.empty();
            }

            TicketVentaAbono ticketVenta = MarshallUtil.leerXML(ticketBean.getTicket(), TicketVentaAbono.class);

            if (ticketVenta == null) {
                log.warn("FacturaDocumentoLoaderImpl.load() - No se pudo convertir el ticket {}", documentUid);
                return Optional.empty();
            }

            SaleDocHdrDTO saleDoc = null;
            try {
                saleDoc = saleDocumentService.findByUid(datosSesion, documentUid);
            }
            catch (ApiException e) {
                log.debug("FacturaDocumentoLoaderImpl.load() - Error obteniendo SaleDoc {}, se continuará sin el DTO", documentUid, e);
            }

            return Optional.of(new FacturaDocumento(ticketBean, ticketVenta, saleDoc));
        }
        catch (Exception e) {
            log.error("FacturaDocumentoLoaderImpl.load() - Error cargando el documento {}", documentUid, e);
            return Optional.empty();
        }
    }
}
