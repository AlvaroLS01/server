package com.comerzzia.api.omnichannel.web.model.basket;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.QueryParam;

import com.comerzzia.omnichannel.domain.dto.basket.items.request.IBasketItemUpdateRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(example = "{ \"upc\": \"1\"}")	
public class BasketItemRequest implements IBasketItemUpdateRequest<BasketItemDataRequest> {
	@QueryParam("upc")
	@NotNull
	@Size(max = 50)
	@Schema(nullable = true, 
	        description = "Unique product code (barcode or internal code)")
	protected String upc;
	
	@QueryParam("scanCodeType")
	protected String scanCodeType;
	
	@QueryParam("scanned")
	protected Boolean scanned;
	
	@QueryParam("attendantId")
	protected String attendantId;
	
	@QueryParam("quantity")
	protected BigDecimal quantity;
	
	@QueryParam("referenceRatePrice")
	protected BigDecimal referenceRatePrice;
	
	@QueryParam("ratePrice")
	@Schema(nullable = true, 
            description = "Rate price. If not null, item rates from comerzzia will not be used")
	protected BigDecimal ratePrice;
	
	@QueryParam("price")
	protected BigDecimal price;
	
	@QueryParam("scaleItemType")
	protected String scaleItemType;
	
	@QueryParam("weight")
	@Schema(nullable = true, 
            description = "It is mandatory only for variable weight items (weight required)")
	protected BigDecimal weight;
	
	@QueryParam("discountPercentage")
	protected BigDecimal discountPercentage;
	
	@QueryParam("serialNumbers")
	protected Set<String> serialNumbers;
	
	@QueryParam("itemData")
	@Schema(nullable = true, 
            description = "Basic item data. If specified, item data from comerzzia will not be used")	
	protected BasketItemDataRequest itemData;
}
