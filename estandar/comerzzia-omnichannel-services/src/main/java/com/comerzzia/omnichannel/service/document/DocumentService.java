package com.comerzzia.omnichannel.service.document;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.entity.document.DocumentContentEntity;
import com.comerzzia.omnichannel.domain.entity.document.DocumentEntity;

public interface DocumentService {

	DocumentEntity findById(IDatosSesion datosSesion, String documentUid);

	DocumentEntity findByDocumentCode(IDatosSesion datosSesion, Long documentTypeId, String documentCode);

	DocumentEntity findByLocatorCode(IDatosSesion datosSesion, String locatorCode);

	void insert(IDatosSesion datosSesion, DocumentEntity record) throws ApiException;

	DocumentContentEntity findContentById(IDatosSesion datosSesion, String documentUid);

	void insertContent(IDatosSesion datosSesion, DocumentContentEntity record) throws ApiException;

}