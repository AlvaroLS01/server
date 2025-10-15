package com.comerzzia.api.omnichannel.web.rest.documents;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.exception.NotFoundException;
import com.comerzzia.api.core.service.util.ComerzziaDatosSesion;
import com.comerzzia.api.omnichannel.web.model.document.Document;
import com.comerzzia.api.omnichannel.web.model.document.NewDocument;
import com.comerzzia.omnichannel.domain.entity.document.DocumentEntity;
import com.comerzzia.omnichannel.service.document.DocumentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("/document")
@Tag(name = "Documents", description = "Documents services")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Controller
public class DocumentResource {
	@Autowired
	DocumentService service;
	
	@Resource(name = "datosSesionRequest")
	protected ComerzziaDatosSesion datosSesionRequest;
	
	@Autowired
	protected ModelMapper modelMapper;
	
	@GET
	@Path("/{documentUid}")
	@Operation(summary = "Get document by uid", description = "Get document by uid", responses = {
			@ApiResponse(description = "The record"),
			@ApiResponse(responseCode = "400", description = "Invalid input values"),
			@ApiResponse(responseCode = "404", description = "Record not found") })
	public Document getDocumentByUid(@PathParam("documentUid") String documentUid) throws ApiException {
		DocumentEntity record = service.findById(datosSesionRequest.getDatosSesionBean(), documentUid);
		
		if (record == null) {
			throw new NotFoundException();
		}
		return modelMapper.map(record, Document.class);  
	}
	
	@GET
	@Path("/{documentTypeId}/{documentCode}")
	@Operation(summary = "Get document by type and code", description = "Get document by type and code", responses = {
			@ApiResponse(description = "The record"),
			@ApiResponse(responseCode = "400", description = "Invalid input values"),
			@ApiResponse(responseCode = "404", description = "Record not found") })
	public Document getDocumentByDocumentCode(@PathParam("documentTypeId") 
												    Long documentTypeId,
												    @PathParam("documentCode") 
	                                                String documentCode) throws ApiException {
		DocumentEntity record = service.findByDocumentCode(datosSesionRequest.getDatosSesionBean(), documentTypeId, documentCode);
		
		if (record == null) {
			throw new NotFoundException();
		}
		return modelMapper.map(record, Document.class);  
	}

	
	@POST
	@Operation(summary = "Insert document", description = "Insert document", responses = {
			@ApiResponse(description = "The record"),
			@ApiResponse(responseCode = "400", description = "Invalid input values") })	
	public Document createDocument(@Valid
                                   NewDocument newRecord) throws ApiException {
		
		if (newRecord.getLocatorCode() == null) {
			newRecord.setLocatorCode(newRecord.getDocumentUid());
		}
				
        service.insert(datosSesionRequest.getDatosSesionBean(), modelMapper.map(newRecord, DocumentEntity.class));
		
        return getDocumentByUid(newRecord.getDocumentUid());        
	}

}
