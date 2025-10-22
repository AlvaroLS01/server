package com.comerzzia.api.omnichannel.web.rest.salesdoc;

import java.util.Optional;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.exception.NotFoundException;
import com.comerzzia.api.core.service.util.ComerzziaDatosSesion;
import com.comerzzia.api.omnichannel.web.model.document.PrintDocumentRequest;
import com.comerzzia.api.omnichannel.web.model.document.PrintDocumentResponse;
import com.comerzzia.api.omnichannel.web.model.saledoc.SaleDocHdr;
import com.comerzzia.bricodepot.api.omnichannel.api.services.facturas.SalesDocumentPrintService;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;
import com.comerzzia.omnichannel.domain.dto.saledoc.SaleDocHdrDTO;
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

        @Resource(name = "datosSesionRequest")
        protected ComerzziaDatosSesion datosSesionRequest;

        @Autowired
        protected ModelMapper modelMapper;

        @Autowired
        protected SalesDocumentPrintService salesDocumentPrintService;

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
        @Produces(MediaType.APPLICATION_JSON)
        @Operation(summary = "Print sales document by uid",
                   description = "Returns the sales document rendered as PDF in base64 encoding",
                   responses = {
                        @ApiResponse(description = "Base64 encoded PDF",
                                     content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                                        schema = @Schema(implementation = PrintDocumentResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid input values"),
                        @ApiResponse(responseCode = "404", description = "Record not found")
                   })
        public Response printSaleDocumentByUid(@PathParam("documentUid") String documentUid,
                                                 @Valid @BeanParam PrintDocumentRequest printDocumentRequest) throws ApiException {

                if (StringUtils.isEmpty(printDocumentRequest.getMimeType())) {
                    printDocumentRequest.setMimeType("application/pdf");
                }

                if (StringUtils.isEmpty(printDocumentRequest.getOutputDocumentName())) {
                        printDocumentRequest.setOutputDocumentName(documentUid);
                }

                if (printDocumentRequest.getInline() == null) {
                        printDocumentRequest.setInline(false);
                }

                PrintDocumentDTO dto = modelMapper.map(printDocumentRequest, PrintDocumentDTO.class);
                dto.setMimeType("application/pdf");

                Optional<PrintDocumentResponse> response = salesDocumentPrintService.print(documentUid,
                                datosSesionRequest.getDatosSesionBean(), printDocumentRequest, dto);

                return Response.ok(response.orElse(null), MediaType.APPLICATION_JSON).build();
        }
}
