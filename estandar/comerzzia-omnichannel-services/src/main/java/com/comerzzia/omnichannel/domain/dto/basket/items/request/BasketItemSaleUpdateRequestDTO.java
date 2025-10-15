package com.comerzzia.omnichannel.domain.dto.basket.items.request;

import java.math.BigDecimal;
import java.util.Set;

import com.comerzzia.omnichannel.domain.dto.basket.items.BasketItemDataDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasketItemSaleUpdateRequestDTO implements IBasketItemUpdateRequest<BasketItemDataDTO> {
	protected String attendantId;
	protected BigDecimal quantity;
	protected BigDecimal price;
	protected BigDecimal weight;
	protected BigDecimal discountPercentage;	
	
	protected Set<String> serialNumbers;
	protected BasketItemDataDTO itemData;	
}
