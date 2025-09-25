package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.util.ComerzziaDatosSesion;
import com.comerzzia.api.v2.facturacionmagento.services.facturacion.FacturacionService;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.FacturacionRequest;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.FacturacionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("/facturacion")
@Tag(name = "Facturacion", description = "Facturacion")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Controller
public class FacturacionRest {

	protected static final Logger log = Logger.getLogger(FacturacionRest.class);
	
	@Resource(name = "datosSesionRequest")
	ComerzziaDatosSesion datosSesionRequest;
	
	@Autowired
	private FacturacionService facturacionService;
	
        @POST
        @Operation(summary = "Facturacion", description = "Facturacion de pedidos", responses = {
        @ApiResponse(description = "The record data"),
        @ApiResponse(responseCode = "404", description = "Record not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})
        @RequestBody(content = {
                        @Content(mediaType = MediaType.APPLICATION_JSON,
                                        schema = @Schema(implementation = FacturacionRequest.class),
                                        examples = @ExampleObject(name = "Devolución con documento origen manual",
                                                        summary = "Incluye los datos del ticket origen cuando no está en Comerzzia",
                                                        value = "{\n  \"ticket\": {\n    \"ticketHeader\": {\n      \"invoiceDocumentType\": \"NC\",\n      \"documentOriginData\": {\n        \"serie\": \"9001\",\n        \"caja\": \"02\",\n        \"numeroFactura\": \"438\",\n        \"idTipoDocumento\": \"1\",\n        \"codTipoDocumento\": \"FS\",\n        \"desTipoDocumento\": \"FACTURA SIMPLIFICADA\",\n        \"uidTicket\": \"aa1731f6-c84c-4bf3-8fee-03c6648e18d1\",\n        \"codTicket\": \"FS 2025900102/00000438\",\n        \"recoveredOnline\": true,\n        \"fecha\": \"2025-09-24T10:55:50+02:00\"\n      }\n    }\n  }\n}")) })
        public FacturacionResponse facturar(@Parameter(description = "Request data") FacturacionRequest request) throws ApiException {
		log.debug("facturar() - Inicio del servicio REST de facturación");
		
 		FacturacionResponse response = facturacionService.facturar(datosSesionRequest.getDatosSesionBean(), request);
		
		return response;
	}

}
