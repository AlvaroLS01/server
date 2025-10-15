package com.comerzzia.api.omnichannel.web.rest.basket;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.exception.BadRequestException;
import com.comerzzia.api.omnichannel.web.model.basket.BasketCalculateRequest;
import com.comerzzia.api.omnichannel.web.model.basket.CouponRequest;
import com.comerzzia.api.omnichannel.web.model.basket.BasketItemRequest;
import com.comerzzia.api.omnichannel.web.model.basket.StartTransactionRequest;
import com.comerzzia.api.omnichannel.web.model.basket.VoidTransactionRequest;
import com.comerzzia.api.omnichannel.web.model.basket.response.BasketCalculateResponse;
import com.comerzzia.api.omnichannel.web.model.basket.response.BasketTransactionResponse;
import com.comerzzia.api.omnichannel.web.model.basket.response.TransactionVoidedResponse;
import com.comerzzia.api.omnichannel.web.model.basket.response.items.BasketItemException;
import com.comerzzia.omnichannel.domain.dto.basket.CouponDTO;
import com.comerzzia.omnichannel.domain.dto.basket.LoyaltySettingsDTO;
import com.comerzzia.omnichannel.domain.dto.basket.PriceSettingsDTO;
import com.comerzzia.omnichannel.domain.dto.basket.StartTransactionDTO;
import com.comerzzia.omnichannel.domain.dto.basket.TransactionVoidedDTO;
import com.comerzzia.omnichannel.domain.dto.basket.VoidTransactionDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.exception.ItemExceptionDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.request.BasketItemRequestDTO;
import com.comerzzia.omnichannel.service.basket.BasketManager;
import com.comerzzia.pos.services.core.sesion.Sesion;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("/basket/{storeCode}/{tillCode}")
@Tag(name = "Basket", description = "Basket services")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Controller
@Timed
public class BasketResource {
	@Autowired
	BasketManager basketManager;
	
	@Autowired
	Sesion sesion;
	
	@Autowired
	protected ModelMapper modelMapper;
	
	Counter basketCalculateCounter;
	
	public BasketResource(MeterRegistry registry) {
	}
	
//	@POST
//	@Path("/calculatemodel")
//	@Produces({ MediaType.APPLICATION_XML })
//	@Operation(summary = "Calculate basket model TEST", description = "Calculate modelbasket without persist", responses = {
//			@ApiResponse(description = "The basket model TEST"),
//			@ApiResponse(responseCode = "400", description = "Invalid input values") })	
//	public Response calculateBasketModel(@PathParam("storeCode") String storeCode,
//			                           @PathParam("tillCode") String tillCode,
//			                           BasketCalculateRequest basketCalculateRequest) throws ApiException {
//        if (basketCalculateRequest.getItems() == null) {
//        	throw new BadRequestException("no lines specified");
//        }
//        
//        sesion.setTillSession(storeCode, tillCode);
//        
//        StartTransactionDTO startTransaction = new StartTransactionDTO();
//        if (basketCalculateRequest.getPriceSettings() != null) {
//           startTransaction.setPriceSettings(modelMapper.map(basketCalculateRequest.getPriceSettings(), PriceSettingsDTO.class));
//        }
//        if (basketCalculateRequest.getLoyaltySettings() != null) {
//           startTransaction.setLoyaltySettings(modelMapper.map(basketCalculateRequest.getLoyaltySettings(), LoyaltySettingsDTO.class));
//        }
//        
//		basketManager.initilizeBasket(startTransaction);
//		
//		if (basketCalculateRequest.getLoyaltyCard() != null) {
//			ItemDTO item = new ItemDTO();
//			item.setUpc(basketCalculateRequest.getLoyaltyCard());
//			
//			basketManager.getBasketItemsManager().newItemRequest(item);
//		}
//
//		for (Item item : basketCalculateRequest.getItems()) {
//			try {
//				basketManager.getBasketItemsManager().newItemSale(modelMapper.map(item, ItemDTO.class));
//			} catch (ItemExceptionDTO e) {
//				throw new BadRequestException("Item '" + item.getUpc() + "' exception: " + e.getMessage() + "\n" + e.toString());
//			}
//		}
//		
//		basketManager.recalculateBasket();
//		basketManager.getBasketItemsManager().updateItemsSoldDiscounts();    		
//		
//        com.comerzzia.omnichannel.model.documents.v4_8.ticket.TicketVentaAbono ticketModel = basketManager.getModelo();
//
//		StringWriter sw = new StringWriter();
//        
//		JAXBContext context;
//		try {
//			context = JAXBContext.newInstance(com.comerzzia.omnichannel.model.documents.v4_8.ticket.TicketVentaAbono.class);
//			Marshaller mar = context.createMarshaller();
//			mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//			mar.marshal(ticketModel, sw);			
//		} catch (JAXBException e) {
//			throw new ApiException(e.getMessage());
//		}
//		
//        return Response.ok(sw.toString()).build();        
//	}

	@POST
	@Path("/calculate")
	@Operation(summary = "Calculate basket", description = "Calculate basket without persist",  responses = {
			@ApiResponse(description = "The basket"),
			@ApiResponse(responseCode = "400", description = "Invalid input values") })	
	public BasketCalculateResponse calculateBasket(@PathParam("storeCode") String storeCode,
			                           @PathParam("tillCode") String tillCode,
			                           BasketCalculateRequest basketCalculateRequest) throws ApiException {
        if (basketCalculateRequest.getItems() == null) {
        	throw new BadRequestException("no lines specified");
        }
        
        sesion.setTillSession(storeCode, tillCode);
        
        StartTransactionDTO startTransaction = new StartTransactionDTO();
        if (basketCalculateRequest.getPriceSettings() != null) {
           startTransaction.setPriceSettings(modelMapper.map(basketCalculateRequest.getPriceSettings(), PriceSettingsDTO.class));
        }
        if (basketCalculateRequest.getLoyaltySettings() != null) {
           startTransaction.setLoyaltySettings(modelMapper.map(basketCalculateRequest.getLoyaltySettings(), LoyaltySettingsDTO.class));
        }
        
		basketManager.initilizeBasket(startTransaction);
		
		List<BasketItemException> itemsExceptions = new ArrayList<BasketItemException>();
		
		// items
		for (BasketItemRequest item : basketCalculateRequest.getItems()) {
			try {
				basketManager.getBasketItemsManager().newItemSale(modelMapper.map(item, BasketItemRequestDTO.class));				
			} catch (ItemExceptionDTO e) {
				itemsExceptions.add(modelMapper.map(e, BasketItemException.class));
			}
		}

		// coupons
		if (basketCalculateRequest.getCoupons() != null) {
			for (CouponRequest couponRequest : basketCalculateRequest.getCoupons()) {   		   
				basketManager.applyCoupon(modelMapper.map(couponRequest, CouponDTO.class));	   
			}
		}
		
		basketManager.recalculateBasket();
		basketManager.getBasketItemsManager().updateItemsSoldDiscounts();    		
		
		// build response
		BasketCalculateResponse response = modelMapper.map(basketManager.getBasketTransaction(), BasketCalculateResponse.class);
		
		if (itemsExceptions.size() > 0) {
			response.setItemsExceptions(itemsExceptions);
		}
		
        return response;        
	}

	@GET
	@Path("/transaction/{id}")
	@Operation(summary = "Get basket transaction", description = "Get basket transaction", deprecated = true, responses = {
			@ApiResponse(description = "The basket"),
			@ApiResponse(responseCode = "400", description = "Invalid input values"),
			@ApiResponse(responseCode = "404", description = "Basket not found") })
	public BasketTransactionResponse getBasketTransaction(@PathParam("storeCode") String storeCode,
								            @PathParam("tillCode") String tillCode,
								            @PathParam("id") String id) throws ApiException {
		sesion.setTillSession(storeCode, tillCode);
		
		basketManager.loadBasket(id);
		
		return modelMapper.map(basketManager.getBasketTransaction(), BasketTransactionResponse.class);  
	}

	
	@POST
	@Path("/transaction")	
	@Operation(summary = "Start persistable basket transaction", description = "Start persistable basket transaction", deprecated = true, responses = {
			@ApiResponse(description = "The basket"),
			@ApiResponse(responseCode = "400", description = "Invalid input values") })
	public BasketTransactionResponse startBasketTransaction(@PathParam("storeCode") String storeCode,
									            @PathParam("tillCode") String tillCode,
									            @Valid StartTransactionRequest startTransaction) throws ApiException {
		sesion.setTillSession(storeCode, tillCode);
		
		basketManager.initilizeBasket(modelMapper.map(startTransaction, StartTransactionDTO.class));
		
		if (startTransaction.getStartItem() != null) {
			basketManager.getBasketItemsManager().newItemRequest(modelMapper.map(startTransaction.getStartItem(), BasketItemRequestDTO.class));			
		}
		
		basketManager.persistBasket();

		return modelMapper.map(basketManager.getBasketTransaction(), BasketTransactionResponse.class);  
	}
	
	@DELETE
	@Path("/transaction/{id}")
	@Operation(summary = "Delete basket transaction", description = "Delete basket transaction", deprecated = true, responses = {
			@ApiResponse(description = "The basket"),
			@ApiResponse(responseCode = "400", description = "Invalid input values"),
			@ApiResponse(responseCode = "404", description = "Basket not found") })
	public TransactionVoidedResponse voidBasketTransaction(@PathParam("storeCode") String storeCode,
								            @PathParam("tillCode") String tillCode,
								            @PathParam("id") String id,
								            @Valid VoidTransactionRequest voidTransactionRequest) throws ApiException {
		sesion.setTillSession(storeCode, tillCode);
		
		basketManager.loadBasket(id);
		
		TransactionVoidedDTO transactionVoidedDTO = basketManager.voidTransaction(modelMapper.map(voidTransactionRequest, VoidTransactionDTO.class));
				
		return modelMapper.map(transactionVoidedDTO, TransactionVoidedResponse.class);  
	}
	
	@PUT
	@Path("transaction/{id}/empty")
	@Operation(deprecated = true)
	public BasketTransactionResponse emptyBasketTransaction(@PathParam("storeCode") String storeCode,
								            @PathParam("tillCode") String tillCode,
								            @PathParam("id") String id) throws ApiException {
//		TODO Hacer lógica para vaciar la cesta
		sesion.setTillSession(storeCode, tillCode);
		
		basketManager.loadBasket(id);
		
		return modelMapper.map(basketManager.getBasketTransaction(), BasketTransactionResponse.class);  
	}
	
	@PUT
	@Path("transaction/{id}/promotionalCode/{promotionalCode}")
	@Operation(deprecated = true)
	public BasketTransactionResponse applyPromotionalCode(@PathParam("storeCode") String storeCode,
								            @PathParam("tillCode") String tillCode,
								            @PathParam("id") String id,
								            @PathParam("promotionalCode") String promotionalCode) throws ApiException {
//		TODO Hacer lógica de aplicar código promocional
		sesion.setTillSession(storeCode, tillCode);
		
		basketManager.loadBasket(id);
		
		return modelMapper.map(basketManager.getBasketTransaction(), BasketTransactionResponse.class);  
	}
}
