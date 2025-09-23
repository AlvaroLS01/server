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
	public FacturacionResponse facturar(@Parameter(description = "Request data") FacturacionRequest request) throws ApiException {
		log.debug("facturar() - Inicio del servicio REST de facturación");
		
 		FacturacionResponse response = facturacionService.facturar(datosSesionRequest.getDatosSesionBean(), request);
		
		return response;
	}

}
