package com.comerzzia.api.omnichannel.web.model.basket.response.items;

import java.math.BigDecimal;
import java.util.Set;

import javax.ws.rs.QueryParam;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class BasketItem extends BasicBasketItem {
	@QueryParam("quantity")
	protected BigDecimal quantity;
	
	@QueryParam("referenceRatePrice")
	protected BigDecimal referenceRatePrice;
	
	@QueryParam("ratePrice")
	protected BigDecimal ratePrice;
	
	@QueryParam("price")
	protected BigDecimal price;
	
	@QueryParam("weight")
	protected BigDecimal weight;
	
	@QueryParam("discountPercentage")
	protected BigDecimal discountPercentage;
	
	@QueryParam("serialNumbers")
	protected Set<String> serialNumbers;
}
