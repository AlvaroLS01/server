package com.comerzzia.bricodepot.api.omnichannel.web.rest.salesdoc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.util.ComerzziaDatosSesion;
import com.comerzzia.api.omnichannel.web.model.document.PrintDocumentRequest;
import com.comerzzia.api.omnichannel.web.rest.salesdoc.SalesDocumentResource;
import com.comerzzia.bricodepot.api.omnichannel.domain.salesdocument.BricodepotPrintableDocument;
import com.comerzzia.bricodepot.api.omnichannel.services.salesdocument.BricodepotSaleDocumentPrintService;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("/salesdocument")
@Tag(name = "Sales documents", description = "Sales documents services")
@Controller("salesDocumentResource")
@Produces(MediaType.APPLICATION_JSON)
public class BricodepotSalesDocumentResource extends SalesDocumentResource {

	private static final String APPLICATION_PDF = "application/pdf";

	private final BricodepotSaleDocumentPrintService saleDocumentPrintService;

	@Autowired
	public BricodepotSalesDocumentResource(BricodepotSaleDocumentPrintService saleDocumentPrintService) {
		this.saleDocumentPrintService = saleDocumentPrintService;
	}

	@Override
	@GET
	@Path("/{documentUid}/print")
	@Operation(summary = "Print sales document by uid", description = "Print sales document by uid returning a base64 encoded payload", responses = {
	        @ApiResponse(description = "The print output", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = BricodepotPrintableDocument.Response.class))),
	        @ApiResponse(responseCode = "400", description = "Invalid input values"), @ApiResponse(responseCode = "404", description = "Record not found") })
	public Response printSaleDocumentByUid(@PathParam("documentUid") String documentUid, @Context HttpServletRequest request, @Context HttpServletResponse response,
	        @Valid @BeanParam PrintDocumentRequest printDocumentRequest) throws ApiException {

		PrintDocumentRequest effectiveRequest = printDocumentRequest != null ? printDocumentRequest : new PrintDocumentRequest();

		if (StringUtils.isBlank(effectiveRequest.getMimeType())) {
			effectiveRequest.setMimeType(APPLICATION_PDF);
		}

		if (StringUtils.isBlank(effectiveRequest.getOutputDocumentName())) {
			effectiveRequest.setOutputDocumentName(documentUid);
		}

		if (effectiveRequest.getInline() == null) {
			effectiveRequest.setInline(Boolean.FALSE);
		}

		PrintDocumentDTO printDocumentDTO = super.modelMapper.map(effectiveRequest, PrintDocumentDTO.class);

		Map<String, String> requestCustomParams = effectiveRequest.getCustomParams();
		if (requestCustomParams != null && !requestCustomParams.isEmpty()) {
			printDocumentDTO.getCustomParams().putAll(requestCustomParams);
		}

		ComerzziaDatosSesion datosSesion = super.datosSesionRequest;
		BricodepotPrintableDocument printableDocument = saleDocumentPrintService.printDocument(datosSesion.getDatosSesionBean(), documentUid, printDocumentDTO);

		BricodepotPrintableDocument.Response responseBody = printableDocument.toResponse(effectiveRequest.getMimeType());

		return Response.ok(responseBody, MediaType.APPLICATION_JSON).build();
	}
}
