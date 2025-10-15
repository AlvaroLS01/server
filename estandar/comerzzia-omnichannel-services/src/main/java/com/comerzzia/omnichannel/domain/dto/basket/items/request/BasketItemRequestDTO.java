package com.comerzzia.omnichannel.domain.dto.basket.items.request;

import java.math.BigDecimal;
import java.util.Set;

import com.comerzzia.omnichannel.domain.dto.basket.items.BasketItemDataDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasketItemRequestDTO  implements IBasketItemUpdateRequest<BasketItemDataDTO> {
	protected String upc;
	protected Boolean scanned;	
	protected String attendantId;
	
	protected BigDecimal quantity;
	protected BigDecimal referenceRatePrice;
	protected BigDecimal ratePrice;
	protected BigDecimal price;

	protected BigDecimal weight;
	protected BigDecimal discountPercentage;
	
	protected String combination1Code;
	protected String combination2Code;
	
	protected Set<String> serialNumbers;
	
	protected BasketItemDataDTO itemData;
}
