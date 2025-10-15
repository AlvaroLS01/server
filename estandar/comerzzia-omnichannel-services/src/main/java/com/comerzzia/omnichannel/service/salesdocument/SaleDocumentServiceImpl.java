package com.comerzzia.omnichannel.service.salesdocument;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.exception.BadRequestException;
import com.comerzzia.api.core.service.exception.NotFoundException;
import com.comerzzia.core.model.tiposdocumentos.TipoDocumentoBean;
import com.comerzzia.core.servicios.ContextHolder;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoException;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoNotFoundException;
import com.comerzzia.core.servicios.tipodocumento.TiposDocumentosService;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;
import com.comerzzia.omnichannel.domain.dto.saledoc.SaleDocHdrDTO;
import com.comerzzia.omnichannel.domain.entity.document.DocumentContentEntity;
import com.comerzzia.omnichannel.domain.entity.document.DocumentEntity;
import com.comerzzia.omnichannel.repository.saledoc.SaleDocumentMapper;
import com.comerzzia.omnichannel.service.document.DocumentService;
import com.comerzzia.omnichannel.service.documentprint.DocumentPrintService;
import com.comerzzia.omnichannel.service.salesdocument.converters.SaleDocumentConverter;
import com.comerzzia.omnichannel.service.salesdocument.metadata.DocumentMetadata;
import com.comerzzia.omnichannel.service.salesdocument.metadata.DocumentMetadataParser;

@Service
public class SaleDocumentServiceImpl implements SaleDocumentService {
	@Value("${comerzzia.omnichannel.onlineMode:false}")
	Boolean onlineMode = false;
	
	@Autowired
	SaleDocumentMapper saleDocumentMapper;
		
	@Autowired
	DocumentService documentService;
	
	@Autowired
	TiposDocumentosService tiposDocumentosService;
		
	@Autowired
	DocumentPrintService printService;
	
	@Autowired
	DocumentMetadataParser cocumentMetadataParser;
		
	@Override
	public SaleDocHdrDTO findByUid(IDatosSesion datosSesion, String documentUid) throws ApiException {		
		SaleDocHdrDTO result = null;
		
		if (onlineMode) {
			result = saleDocumentMapper.selectByDocumentUidDTO(datosSesion.getUidActividad(), documentUid);
		}
		
		if (result == null) {
			result = getSaleDocumentByDocumentUid(datosSesion, documentUid);
		}
		
		if (result == null) {
			throw new NotFoundException();
		}
		
		return result;
	}
	
	@Override
	public SaleDocHdrDTO findByDocumentCode(IDatosSesion datosSesion, Long documentTypeId, String documentCode) throws ApiException {		
		SaleDocHdrDTO result = null;
		
		if (onlineMode) {
			result = saleDocumentMapper.selectByDocumentCodeDTO(datosSesion.getUidActividad(), documentTypeId, documentCode);
		}
		
		if (result == null) {
			result = getSaleDocumentByDocumentCode(datosSesion, documentTypeId, documentCode);
		}
		
		if (result == null) {
			throw new NotFoundException();
		}
		
		return result;
	}
	
	@Override
	public void printDocument(OutputStream outputStream, IDatosSesion datosSesion, String documentUid, PrintDocumentDTO printRequest) throws ApiException {
		if (onlineMode) {
			DocumentContentEntity documentContent = documentService.findContentById(datosSesion, documentUid);
			
			if (documentContent != null) {			
				if (StringUtils.equals(printRequest.getMimeType(), documentContent.getMimeType())) {
					// return stored document content
					try {
						outputStream.write(documentContent.getDocumentContent());
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					return;
				}
			}
		}
		
		DocumentEntity record = documentService.findById(datosSesion, documentUid);
		
		if (record == null) {
			throw new NotFoundException();
		}
		
		SaleDocumentConverter<?> converter = getDocumentConverter(datosSesion, record);
		
		converter.printDocument(outputStream, datosSesion, record, printRequest);		
	}
	
	protected SaleDocHdrDTO getSaleDocumentByDocumentUid(IDatosSesion datosSesion, String documentUid) throws ApiException {
		DocumentEntity document = documentService.findById(datosSesion, documentUid);
		
		if (document == null) {
			return null;
		}
		
		return fromDocumentToSaleDocument(datosSesion, document);
	}
	
	protected SaleDocHdrDTO getSaleDocumentByDocumentCode(IDatosSesion datosSesion, Long documentTypeId, String documentCode) throws ApiException {
		DocumentEntity document = documentService.findByDocumentCode(datosSesion, documentTypeId, documentCode);
		
		if (document == null) {
			return null;
		}
		
		return fromDocumentToSaleDocument(datosSesion, document);
	}	
	
	protected SaleDocumentConverter<?> getDocumentConverter(IDatosSesion datosSesion, DocumentEntity document) throws ApiException {
		TipoDocumentoBean tipoDocumento;
		try {
			tipoDocumento = tiposDocumentosService.consultar(datosSesion, document.getDocumentTypeId());
		} catch (TipoDocumentoNotFoundException | TipoDocumentoException e1) {
			throw new RuntimeException(e1);
		}
		
		// check if a sale document
		if (!(StringUtils.equals(tipoDocumento.getCodAplicacion(), "VENT") ||
			  StringUtils.equals(tipoDocumento.getCodAplicacion(), "SALE"))) {
			throw new BadRequestException("The document is not of type Sale");
		}
		
		DocumentMetadata metadata = cocumentMetadataParser.getMetadata(datosSesion, document);
		
		SaleDocumentConverter<?> converter;
		try {
			String version = metadata.getSchemaVersion().replace(".", "_");
			
			converter = ((SaleDocumentConverter<?>)ContextHolder.getBean(tipoDocumento.getCodTipoDocumento() + "_" + version + "_DocumentConverter"));
		} catch (ClassNotFoundException e) {
			throw new ApiException("SalesDocumentConverter not found for document type " + tipoDocumento.getCodTipoDocumento() + " schema version " + metadata.getSchemaVersion());
		}
		
		return converter;
	}
	
	protected SaleDocHdrDTO fromDocumentToSaleDocument(IDatosSesion datosSesion, DocumentEntity document) throws ApiException {
		return getDocumentConverter(datosSesion, document).convert(datosSesion, document);
	}
	
	protected Object fromDocumentToSaleDocumentObject(IDatosSesion datosSesion, DocumentEntity document) throws ApiException {
		return getDocumentConverter(datosSesion, document).convert(document.getDocumentContent());
	}
	
}
