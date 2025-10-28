package com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.bricodepot.api.omnichannel.api.domain.salesdocument.BricodepotPrintableDocument;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;

public interface BricodepotSaleDocumentPrintService {

        BricodepotPrintableDocument printDocument(IDatosSesion datosSesion, String documentUid, PrintDocumentDTO printRequest) throws ApiException;
}
