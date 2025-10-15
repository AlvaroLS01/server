package com.comerzzia.omnichannel.domain.dto.basket.items.request;

import java.math.BigDecimal;
import java.util.Set;

public interface IBasketItemUpdateRequest<T> {
	BigDecimal getQuantity();
	BigDecimal getPrice();
	
	BigDecimal getWeight();
	
	BigDecimal getDiscountPercentage();
	
	Set<String> getSerialNumbers();
	
	T getItemData();
}
