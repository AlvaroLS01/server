package com.comerzzia.bricodepot.api.omnichannel.services.salesdocument;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.bricodepot.api.omnichannel.domain.salesdocument.BricodepotPrintableDocument;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;

public interface BricodepotSaleDocumentPrintService {

	BricodepotPrintableDocument printDocument(IDatosSesion datosSesion, String documentUid, PrintDocumentDTO printRequest) throws ApiException;
}
