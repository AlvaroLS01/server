package com.comerzzia.omnichannel.service.salesdocument.converters;

import java.io.OutputStream;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;
import com.comerzzia.omnichannel.domain.dto.saledoc.SaleDocHdrDTO;
import com.comerzzia.omnichannel.domain.entity.document.DocumentEntity;
import com.comerzzia.omnichannel.service.salesdocument.metadata.DocumentMetadata;

public interface SaleDocumentConverter<T> {

	SaleDocHdrDTO convert(IDatosSesion datosSesion, DocumentEntity document);
    T convert(final byte[] content);
	DocumentMetadata getMetadata(IDatosSesion datosSesion, DocumentEntity document);
	void printDocument(OutputStream outputStream, IDatosSesion datosSesion, DocumentEntity document,
			PrintDocumentDTO printRequest) throws ApiException;
}