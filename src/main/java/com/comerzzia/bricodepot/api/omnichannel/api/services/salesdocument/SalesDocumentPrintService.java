package com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument;

import java.util.Optional;

import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.model.SalesDocumentPrintRequest;
import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.model.SalesDocumentPrintResult;

public interface SalesDocumentPrintService {

    Optional<SalesDocumentPrintResult> print(String documentUid, SalesDocumentPrintRequest request);
}

