package com.comerzzia.bricodepot.api.omnichannel.api.services.facturas;

import java.util.Optional;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.omnichannel.web.model.document.PrintDocumentRequest;
import com.comerzzia.api.omnichannel.web.model.document.PrintDocumentResponse;
import com.comerzzia.core.servicios.sesion.DatosSesionBean;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;

public interface SalesDocumentPrintService {

        Optional<PrintDocumentResponse> print(String documentUid, DatosSesionBean datosSesion,
                        PrintDocumentRequest request, PrintDocumentDTO printDocumentDTO) throws ApiException;
}
