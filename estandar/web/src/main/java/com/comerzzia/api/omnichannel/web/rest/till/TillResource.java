package com.comerzzia.api.omnichannel.web.rest.till;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.exception.BadRequestException;
import com.comerzzia.api.core.service.exception.NotFoundException;
import com.comerzzia.api.omnichannel.web.model.till.response.CashJournalHdr;
import com.comerzzia.api.omnichannel.web.model.till.response.CashJournalResponse;
import com.comerzzia.api.omnichannel.web.model.till.response.PaymentMethod;
import com.comerzzia.omnichannel.service.basket.BasketManager;
import com.comerzzia.omnichannel.service.till.TillService;
import com.comerzzia.pos.services.cajas.CajaEstadoException;
import com.comerzzia.pos.services.cajas.CajasServiceException;
import com.comerzzia.pos.services.cajas.CashJournalDTO;
import com.comerzzia.pos.services.core.sesion.Sesion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("/till/{storeCode}/{tillCode}")
@Tag(name = "Till", description = "Till services")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Controller
public class TillResource {
	@Autowired
	TillService service;

	@Autowired
	Sesion sesion;

	@Autowired
	BasketManager basketManager;
	
	@Autowired
	protected ModelMapper modelMapper;

	@GET
	@Path("/availablePaymentsMethods")
	@Operation(summary = "Get available payments methods", description = "Get available payments methods", responses = {
			@ApiResponse(description = "The payment methods list") })
	public List<PaymentMethod> getAvailablePaymentMethods(@PathParam("storeCode") String storeCode,
			@PathParam("tillCode") String tillCode) throws ApiException {
		sesion.setTillSession(storeCode, tillCode);
				
	    return modelMapper.map(service.getAvailablePaymentMethods(sesion), new TypeToken<List<PaymentMethod>>() {
	      }.getType());
	}

	@GET
	@Operation(summary = "Get current till", description = "Get current till", responses = {
			@ApiResponse(description = "The till record"),
			@ApiResponse(responseCode = "404", description = "Till is not opened") })
	public CashJournalHdr getCurrentTill(@PathParam("storeCode") String storeCode, @PathParam("tillCode") String tillCode)
			throws ApiException {
		sesion.setTillSession(storeCode, tillCode);
		
		CashJournalDTO caja = sesion.getSesionCaja().getCajaAbierta();

		if (caja == null) {
			throw new NotFoundException();		
		}
		
		return modelMapper.map(caja.getCashJournalHdr(), CashJournalHdr.class);
	}

	@GET
	@Path("/status")
	@Operation(summary = "Get current till status", description = "Get current till status details", responses = {
			@ApiResponse(description = "The till record"),
			@ApiResponse(responseCode = "404", description = "Till is not opened") })
	public CashJournalResponse getTillStatus(@PathParam("storeCode") String storeCode, @PathParam("tillCode") String tillCode)
			throws ApiException {
		sesion.setTillSession(storeCode, tillCode);
		
		CashJournalDTO caja = sesion.getSesionCaja().getCajaAbierta();

		if (caja == null) {
			throw new NotFoundException();
		}

		try {
			sesion.getSesionCaja().actualizarDatosCaja();
		} catch (CajasServiceException e) {
			throw new ApiException(e.getMessage(), e);
		}
		
		return modelMapper.map(sesion.getSesionCaja().getCajaAbierta(), CashJournalResponse.class);
	}

	@POST
	@Operation(summary = "Open till", description = "Open till", responses = {
			@ApiResponse(description = "The till record"),
			@ApiResponse(responseCode = "400", description = "Invalid input values") })
	public CashJournalHdr openTill(@PathParam("storeCode") String storeCode, @PathParam("tillCode") String tillCode,
			@QueryParam("startDate") @Parameter(description = "Start date") Date startDate,
			@QueryParam("initialBalance") @Parameter(description = "Initial balance") BigDecimal initialBalance)
			throws ApiException {
		sesion.setTillSession(storeCode, tillCode);
		
		CashJournalDTO caja = sesion.getSesionCaja().getCajaAbierta();

		if (caja != null) {
			throw new ApiException(ApiException.STATUS_RESPONSE_ERROR_CONFLICT_STATE, "The till is currently opened");
		}

		// default values
		if (startDate == null) {
			startDate = new Date();
		} else {
			// TODO: Limit startDate to logical values
		}

		if (initialBalance == null) {
			initialBalance = BigDecimal.ZERO;
		}

		try {
			sesion.getSesionCaja().abrirCajaManual(startDate, initialBalance);
		} catch (CajasServiceException | CajaEstadoException e) {
			throw new ApiException(e.getMessage(), e);
		}

		return getCurrentTill(storeCode, tillCode);
	}

	@PUT
	@Operation(summary = "Close till", description = "Close till", responses = {
			@ApiResponse(responseCode = "204", description = "Empty response"),
			@ApiResponse(responseCode = "400", description = "Invalid input values"),
			@ApiResponse(responseCode = "404", description = "Till is not opened") })
	public Response closeTill(@PathParam("storeCode") String storeCode, @PathParam("tillCode") String tillCode,
			@QueryParam("closingDate") @Parameter(description = "Closing Date") Date closingDate,
			@QueryParam("print") @Parameter(description = "Print") Boolean print) throws ApiException {
		CashJournalResponse caja = getTillStatus(storeCode, tillCode);

		// default values
		if (closingDate == null) {
			closingDate = new Date();
		} else {
			if (caja.getCashJournalHdr().getOpeningDate().compareTo(closingDate) < 0) {
				throw new BadRequestException("The closing date cannot be less than the opening date");
			}
		}

		try {
//			if (sesion.getSesionCaja().tieneDescuadres()) {
//				//TODO: ¿validaciones?
//			}

			sesion.getSesionCaja().guardarCierreCaja(closingDate);

			if (print == null || print) {
				service.printLastTillClosure(sesion);
			}
		} catch (CajasServiceException e) {
			throw new ApiException(e.getMessage(), e);
		}

		return Response.ok().status(Status.NO_CONTENT).build();
	}

	@POST
	@Path("/forceOpen")
	@Operation(summary = "Force till open", description = "Force till open if closed", responses = {
			@ApiResponse(description = "The till record"),
			@ApiResponse(responseCode = "400", description = "Invalid input values") })
	public CashJournalHdr forceOpen(@PathParam("storeCode") String storeCode, @PathParam("tillCode") String tillCode)
			throws ApiException {
		sesion.setTillSession(storeCode, tillCode);
		
		try {
			sesion.getSesionCaja().abrirCajaAutomatica();
		} catch (CajasServiceException | CajaEstadoException e) {
			throw new ApiException(e.getMessage(), e);
		}

		return getCurrentTill(storeCode, tillCode);
	}

//	@POST
//	@Path("/printLastTillClosure")
//	@Operation(summary = "Print last till closure", description = "Print last till closure", responses = {
//			@ApiResponse(responseCode = "204", description = "Empty response"),
//			@ApiResponse(responseCode = "400", description = "Invalid input values") })
//	public Response printLastTillClosure() throws ApiException {
//		service.printLastTillClosure(sesion);
//				
//		return Response.ok().status(Status.NO_CONTENT).build();
//	}
}
