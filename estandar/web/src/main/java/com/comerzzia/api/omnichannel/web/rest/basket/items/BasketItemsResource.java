package com.comerzzia.api.omnichannel.web.rest.basket.items;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
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
import com.comerzzia.api.core.service.exception.NotFoundException;
import com.comerzzia.api.omnichannel.web.model.basket.BasketItemRequest;
import com.comerzzia.api.omnichannel.web.model.basket.BasketItemSaleUpdateRequest;
import com.comerzzia.api.omnichannel.web.model.basket.response.BasketItemsUpdateEvent;
import com.comerzzia.api.omnichannel.web.model.basket.response.items.BasketItemSold;
import com.comerzzia.omnichannel.domain.dto.basket.BasketItemsUpdateEventDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.BasketItemSoldDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.request.BasketItemRequestDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.request.BasketItemSaleUpdateRequestDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.request.BasketItemVoidRequestDTO;
import com.comerzzia.omnichannel.service.basket.BasketManager;
import com.comerzzia.pos.services.core.sesion.Sesion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("/basket/{storeCode}/{tillCode}/transaction/{id}/items")
@Tag(name = "Basket transaction items", description = "Basket transaction items services")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Controller
public class BasketItemsResource {
	@Autowired
	Sesion sesion;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Resource(name = "basketManager")
	BasketManager basketManager;

	@GET
	@Path("/{itemNumber}")
	@Operation(summary = "Get basket transaction", description = "Get basket transaction", deprecated = true, responses = {
			@ApiResponse(description = "The basket"),
			@ApiResponse(responseCode = "400", description = "Invalid input values"),
			@ApiResponse(responseCode = "404", description = "Basket not found") })
	public BasketItemSold getBasketItem(@PathParam("storeCode") String storeCode, @PathParam("tillCode") String tillCode,
			@PathParam("id") String id, @PathParam("itemNumber") int itemNumber) throws ApiException {
		sesion.setTillSession(storeCode, tillCode);
		
		basketManager.loadBasket(id);

		BasketItemSoldDTO response = basketManager.getBasketTransaction().getItems().get(itemNumber);

		if (response == null) {
			throw new NotFoundException(404, "itemNumber not found in basket");
		}

		return modelMapper.map(basketManager.getBasketTransaction().getItems().get(itemNumber), BasketItemSold.class);
	}

	@DELETE
	@Path("/{itemNumber}")
	@Operation(summary = "Delete basket item", description = "Delete basket item", deprecated = true, responses = {
			@ApiResponse(description = "The basket items update event"),
			@ApiResponse(responseCode = "400", description = "Bad imput data"),
			@ApiResponse(responseCode = "404", description = "Record not found") })
	public BasketItemsUpdateEvent deleteBasketItem(@PathParam("storeCode") String storeCode,
			@PathParam("tillCode") String tillCode, @PathParam("id") String id, @PathParam("itemNumber") int itemNumber)
			throws ApiException {
		sesion.setTillSession(storeCode, tillCode);
		
		basketManager.loadBasket(id);

		BasketItemSoldDTO itemSold = basketManager.getBasketTransaction().getItems().get(itemNumber);

		if (itemSold == null) {
			throw new NotFoundException(404, "itemNumber not found in basket");
		}

		BasketItemVoidRequestDTO voidItem = new BasketItemVoidRequestDTO();
		voidItem.setUpc(itemSold.getUpc());
		voidItem.setItemNumber(itemNumber);

		BasketItemsUpdateEventDTO response = basketManager.getBasketItemsManager().voidItemRequest(voidItem);

		basketManager.persistBasket();

		return modelMapper.map(response, BasketItemsUpdateEvent.class);
	}

	@POST
	@Operation(summary = "Insert new item", description = "Insert new item", deprecated = true, responses = {
			@ApiResponse(description = "The basket items update event"),
			@ApiResponse(responseCode = "404", description = "Basket not found") })
	public BasketItemsUpdateEvent insertBasketItem(@PathParam("storeCode") String storeCode,
			@PathParam("tillCode") String tillCode, @PathParam("id") @Parameter(description = "Basket id") String id,
			@Valid @Parameter(description = "New item", required = true) BasketItemRequest item) throws ApiException {

		sesion.setTillSession(storeCode, tillCode);
		
		basketManager.loadBasket(id);
		BasketItemsUpdateEventDTO response = basketManager.getBasketItemsManager().newItemRequest(modelMapper.map(item, BasketItemRequestDTO.class));
		basketManager.persistBasket();

		return modelMapper.map(response, BasketItemsUpdateEvent.class);
	}

	@PUT
	@Path("/{itemNumber}")
	@Operation(summary = "Update basket item", deprecated = true, responses = {
			@ApiResponse(description = "The basket items update event"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "404", description = "Record not found") })
	public BasketItemsUpdateEvent updateBasketItem(@PathParam("storeCode") String storeCode,
			@PathParam("tillCode") String tillCode, @PathParam("id") String id, @PathParam("itemNumber") int itemNumber,
			@BeanParam @Parameter(description = "New item data", required = true) BasketItemSaleUpdateRequest item)
			throws ApiException {
		sesion.setTillSession(storeCode, tillCode);
		
		basketManager.loadBasket(id);

		BasketItemSoldDTO itemSold = basketManager.getBasketTransaction().getItems().get(itemNumber);

		if (itemSold == null) {
			throw new NotFoundException(404, "ItemNumber not found in basket");
		}

		BasketItemsUpdateEventDTO response = basketManager.getBasketItemsManager().itemSaleUpdateRequest(itemNumber, modelMapper.map(item, BasketItemSaleUpdateRequestDTO.class));

		basketManager.persistBasket();

		return modelMapper.map(response, BasketItemsUpdateEvent.class);
	}

}
