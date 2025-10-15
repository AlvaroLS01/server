package com.comerzzia.omnichannel.service.salesdocument;

import java.io.OutputStream;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;
import com.comerzzia.omnichannel.domain.dto.saledoc.SaleDocHdrDTO;

public interface SaleDocumentService {

	SaleDocHdrDTO findByUid(IDatosSesion datosSesion, String documentUid) throws ApiException;

	SaleDocHdrDTO findByDocumentCode(IDatosSesion datosSesion, Long documentTypeId, String documentCode)
			throws ApiException;

	void printDocument(OutputStream outputStream, IDatosSesion datosSesion, String documentUid, PrintDocumentDTO printRequest) throws ApiException;


}