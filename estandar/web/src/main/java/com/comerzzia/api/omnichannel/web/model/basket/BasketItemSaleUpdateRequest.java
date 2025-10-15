package com.comerzzia.api.omnichannel.web.model.basket;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasketItemSaleUpdateRequest {
	
	@QueryParam("quantity")
	@NotNull
	protected BigDecimal quantity;
	
	@QueryParam("price")
	@NotNull
	protected BigDecimal price;
	
	@QueryParam("weight")
	@NotNull
	protected BigDecimal weight;
	
	@QueryParam("discountPercentage")
	@NotNull
	protected BigDecimal discountPercentage;
	
	@QueryParam("serialNumbers")
	@NotNull
	protected Set<String> serialNumbers;
	
	@QueryParam("attendantId")
	@NotNull
	protected String attendantId;
}
