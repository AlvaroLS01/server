package com.comerzzia.api.omnichannel.web.rest.basket.pay;

import javax.annotation.Resource;
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
import com.comerzzia.api.omnichannel.web.model.basket.TenderRequest;
import com.comerzzia.api.omnichannel.web.model.basket.response.pay.BasketPaymentsStatus;
import com.comerzzia.api.omnichannel.web.model.basket.response.pay.PaymentTransactionLineResponse;
import com.comerzzia.omnichannel.domain.dto.basket.pay.PaymentsStatusDto;
import com.comerzzia.omnichannel.domain.dto.basket.pay.TenderDTO;
import com.comerzzia.omnichannel.domain.entity.paytransactionline.PaymentTransactionLineEntity;
import com.comerzzia.omnichannel.service.basket.BasketManager;
import com.comerzzia.omnichannel.service.paytransaction.PayTransactionService;
import com.comerzzia.pos.services.core.sesion.Sesion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("/basket/{storeCode}/{tillCode}/transaction/{id}/payments")
@Tag(name = "Basket transaction payments", description = "Basket transaction payments services")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Controller
public class BasketPaymentsResource {
	@Resource(name = "basketManager")
	BasketManager basketManager;

	@Autowired
	Sesion sesion;

	@Autowired
	PayTransactionService tillTransactionsService;
	
	@Autowired
	ModelMapper modelMapper;

	@GET
	@Path("/{paymentId}")
	@Operation(summary = "Get basket transaction payment status", description = "Get basket transaction payment status", deprecated = true, responses = {
			@ApiResponse(description = "The basket"),
			@ApiResponse(responseCode = "400", description = "Invalid input values"),
			@ApiResponse(responseCode = "404", description = "Basket not found") })
	public PaymentTransactionLineResponse getPaymentStatus(@PathParam("storeCode") String storeCode,
			@PathParam("tillCode") String tillCode, @PathParam("id") @Parameter(description = "Basket id") String id,
			@PathParam("paymentId") @Parameter(description = "Payment id") String paymentId) throws ApiException {
		sesion.setTillSession(storeCode, tillCode);
		
		PaymentTransactionLineEntity paymentTransactionLine = tillTransactionsService.getTransactionDetail(basketManager.getSesion(), paymentId);

		if (paymentTransactionLine == null) {
			throw new NotFoundException();
		}

		PaymentTransactionLineResponse response = modelMapper.map(paymentTransactionLine, PaymentTransactionLineResponse.class);
		
		PaymentsStatusDto paymentsStatus = basketManager.getBasketPaymentsManager().getPaymentStatus();
		
		response.setPaymentsStatus(modelMapper.map(paymentsStatus, BasketPaymentsStatus.class));
		
		return response;
	}

	@POST
	@Operation(summary = "Try pay", description = "Try pay operation", deprecated = true, responses = {
			@ApiResponse(description = "The basket payment id operation"),
			@ApiResponse(responseCode = "404", description = "Basket not found") })
	public PaymentTransactionLineResponse tryPay(@PathParam("storeCode") String storeCode, @PathParam("tillCode") String tillCode,
			@PathParam("id") @Parameter(description = "Basket id") String id,
			TenderRequest tender) throws ApiException {
		sesion.setTillSession(storeCode, tillCode);
		
		basketManager.loadBasket(id);
		PaymentTransactionLineEntity tillTransaction = basketManager.getBasketPaymentsManager().payRequest(modelMapper.map(tender, TenderDTO.class));

		String payTransactionLineUid = tillTransaction.getPayTransactionLineUid();
		return getPaymentStatus(storeCode, tillCode, payTransactionLineUid, payTransactionLineUid);
	}
}
