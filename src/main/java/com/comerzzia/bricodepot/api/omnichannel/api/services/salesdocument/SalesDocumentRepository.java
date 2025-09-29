package com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument;

import java.util.Optional;

import com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument.TicketVentaAbono;

/**
 * Repository abstraction used by the print service to locate sales documents.
 */
@FunctionalInterface
public interface SalesDocumentRepository {

    Optional<TicketVentaAbono> findByDocumentUid(String documentUid);
}

