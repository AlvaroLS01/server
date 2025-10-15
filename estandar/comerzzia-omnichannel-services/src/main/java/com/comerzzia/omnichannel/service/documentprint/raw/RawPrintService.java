package com.comerzzia.omnichannel.service.documentprint.raw;

import java.io.OutputStream;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;

public interface RawPrintService {

	void getRawXML(IDatosSesion datosSesion, OutputStream outputStream, PrintDocumentDTO printRequest)
			throws ApiException;

	void getRawXMLAsHTML(IDatosSesion datosSesion, OutputStream outputStream, PrintDocumentDTO printRequest)
			throws ApiException;

}