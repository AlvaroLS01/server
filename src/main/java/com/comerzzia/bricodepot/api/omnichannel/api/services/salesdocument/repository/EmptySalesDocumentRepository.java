package com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.repository;

import java.util.Optional;

import com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument.TicketVentaAbono;
import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.SalesDocumentRepository;

public class EmptySalesDocumentRepository implements SalesDocumentRepository {

    @Override
    public Optional<TicketVentaAbono> findByDocumentUid(String documentUid) {
        return Optional.empty();
    }
}

