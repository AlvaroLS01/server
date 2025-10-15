package com.comerzzia.omnichannel.service.document;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.exception.BadRequestException;
import com.comerzzia.api.core.service.exception.GeneralErrorKeysConstants;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.entity.document.DocumentContentEntity;
import com.comerzzia.omnichannel.domain.entity.document.DocumentEntity;
import com.comerzzia.omnichannel.repository.document.DocumentMapper;

@Service
public class DocumentServiceImpl implements DocumentService {
	@Autowired
	DocumentMapper documentMapper;

	@Override
	public DocumentEntity findById(IDatosSesion datosSesion, String documentUid) {
		return documentMapper.selectByDocumentUid(datosSesion.getUidActividad(), documentUid);
	}

	@Override
	public DocumentEntity findByDocumentCode(IDatosSesion datosSesion, Long documentTypeId, String documentCode) {
		return documentMapper.selectByDocumentCode(datosSesion.getUidActividad(), documentTypeId, documentCode);
	}

	@Override
	public DocumentEntity findByLocatorCode(IDatosSesion datosSesion, String locatorCode) {
		return documentMapper.selectByDocumentLocatorCode(datosSesion.getUidActividad(), locatorCode);
	}

	@Override
	public void insert(IDatosSesion datosSesion, DocumentEntity record) throws ApiException {
		record.setActivityUid(datosSesion.getUidActividad());

		try {
			documentMapper.insert(record);
		} catch (Exception e) {
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new BadRequestException(ApiException.STATUS_RESPONSE_ERROR_CONFLICT_STATE,
						GeneralErrorKeysConstants.ERROR_RECORD_DUPLICATE_KEY, e.getMessage());
			} else {
				throw new ApiException(ApiException.STATUS_RESPONSE_ERROR_INTERNAL,
						GeneralErrorKeysConstants.ERROR_RECORD_INSERT, e.getMessage());
			}
		}
	}
	
	@Override
	public DocumentContentEntity findContentById(IDatosSesion datosSesion, String documentUid) {
		return documentMapper.selectDocumentContent(datosSesion.getUidActividad(), documentUid);
	}
	
	@Override
	public void insertContent(IDatosSesion datosSesion, DocumentContentEntity record) throws ApiException {
		record.setActivityUid(datosSesion.getUidActividad());

		try {
			documentMapper.insertContent(record);
		} catch (Exception e) {
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new BadRequestException(ApiException.STATUS_RESPONSE_ERROR_CONFLICT_STATE,
						GeneralErrorKeysConstants.ERROR_RECORD_DUPLICATE_KEY, e.getMessage());
			} else {
				throw new ApiException(ApiException.STATUS_RESPONSE_ERROR_INTERNAL,
						GeneralErrorKeysConstants.ERROR_RECORD_INSERT, e.getMessage());
			}
		}
	}	
}
