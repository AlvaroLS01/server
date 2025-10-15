package com.comerzzia.api.omnichannel.web.rest.salesdoc;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.exception.NotFoundException;
import com.comerzzia.api.core.service.util.ComerzziaDatosSesion;
import com.comerzzia.api.omnichannel.web.model.document.PrintDocumentRequest;
import com.comerzzia.api.omnichannel.web.model.saledoc.SaleDocHdr;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;
import com.comerzzia.omnichannel.domain.dto.saledoc.SaleDocHdrDTO;
import com.comerzzia.omnichannel.service.document.DocumentService;
import com.comerzzia.omnichannel.service.salesdocument.SaleDocumentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("/salesdocument")
@Tag(name = "Sales documents", description = "Sales documents services")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Controller
public class SalesDocumentResource {
	@Autowired
	SaleDocumentService service;
	
	@Autowired
	DocumentService documentService;
	
	@Resource(name = "datosSesionRequest")
	protected ComerzziaDatosSesion datosSesionRequest;
	
	@Autowired
	protected ModelMapper modelMapper;
	
	@GET
	@Path("/{documentUid}")
	@Operation(summary = "Get sales document by uid", description = "Get sales document by uid", responses = {
			@ApiResponse(description = "The record"),
			@ApiResponse(responseCode = "400", description = "Invalid input values"),
			@ApiResponse(responseCode = "404", description = "Record not found") })
	public SaleDocHdr getSaleDocumentByUid(@PathParam("documentUid") String documentUid) throws ApiException {
		SaleDocHdrDTO record = service.findByUid(datosSesionRequest.getDatosSesionBean(), documentUid);
		
		if (record == null) {
			throw new NotFoundException();
		}
		return modelMapper.map(record, SaleDocHdr.class);  
	}
	
	@GET
	@Path("/{documentTypeId}/{documentCode}")
	@Operation(summary = "Get sales document by type and code", description = "Get sales document by type and code", responses = {
			@ApiResponse(description = "The record"),
			@ApiResponse(responseCode = "400", description = "Invalid input values"),
			@ApiResponse(responseCode = "404", description = "Record not found") })
	public SaleDocHdr getSaleDocumentByDocumentCode(@PathParam("documentTypeId") 
												    Long documentTypeId,
												    @PathParam("documentCode") 
	                                                String documentCode) throws ApiException {
		SaleDocHdrDTO record = service.findByDocumentCode(datosSesionRequest.getDatosSesionBean(), documentTypeId, documentCode);
		
		if (record == null) {
			throw new NotFoundException();
		}
		return modelMapper.map(record, SaleDocHdr.class);  
	}
	
	
	@GET
	@Path("/{documentUid}/print")
	@Operation(summary = "Print sales document by uid", description = "Print sales document by uid", responses = {
			@ApiResponse(description = "The print output",  
					     content = { 
					    		     @Content(mediaType = "application/xml", schema = @Schema(type = "string", format = "byte")),
					    		     @Content(mediaType = "application/pdf", schema = @Schema(type = "string", format = "byte")),
					    		     @Content(mediaType = "application/jasperprint", schema = @Schema(type = "string", format = "byte")),
					    		     @Content(mediaType = "text/html", schema = @Schema(type = "string", format = "byte"))
					    		   }),
			@ApiResponse(responseCode = "400", description = "Invalid input values"),
			@ApiResponse(responseCode = "404", description = "Record not found") })
	@Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML,  "application/pdf", "application/jasperprint" })
	public Response printSaleDocumentByUid(@PathParam("documentUid") 
	                                         String documentUid,
	                                         @Context HttpServletRequest request, 
	                                         @Context HttpServletResponse response,
	                                         @Valid
	                                         @BeanParam 
	                                         PrintDocumentRequest 
	                                         printDocumentRequest) throws ApiException {

		if (StringUtils.isEmpty(printDocumentRequest.getMimeType())) {
		    printDocumentRequest.setMimeType(request.getContentType());
		}
		
		if (StringUtils.isEmpty(printDocumentRequest.getOutputDocumentName())) {
			printDocumentRequest.setOutputDocumentName(documentUid);
		}
		
		if (printDocumentRequest.getInline() == null) {
			printDocumentRequest.setInline(false);
		}
		
		PrintDocumentDTO printDocumentDTO = modelMapper.map(printDocumentRequest, PrintDocumentDTO.class);
				
		StreamingOutput outputStream = new StreamingOutput() {
			@Override
			public void write(java.io.OutputStream output) throws IOException, WebApplicationException {
				try {
					service.printDocument(output, datosSesionRequest.getDatosSesionBean(), documentUid, printDocumentDTO);
					
					output.flush();
				} catch (Exception e) {
					e.printStackTrace();
					throw new WebApplicationException("Error creating pdf !!. Message: " + e.getMessage(), e);
				}
			}
		};
		
		if (printDocumentDTO.getInline()) {
			return Response.ok(outputStream, printDocumentDTO.getMimeType())
					.header("Content-disposition", "inline; filename = " + printDocumentDTO.getOutputDocumentName() + ".pdf").build();			
		} else {
			return Response.ok(outputStream, MediaType.APPLICATION_OCTET_STREAM)
					.header("Content-disposition", "attachment; filename = " + printDocumentDTO.getOutputDocumentName() + ".pdf").build();
		}
	}
}
