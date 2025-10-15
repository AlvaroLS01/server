package com.comerzzia.api.omnichannel.web.model.basket.response.items;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class BasketItemSalesException extends BasketItemException {
    protected Boolean recalled;
	
	protected Boolean timeRestricted;
	
	protected Boolean weightRequired;	
	
	protected Boolean quantityRequired;
	
	protected Boolean quantityRestricted;
	
	protected Boolean priceRequired;
	
	protected Boolean serialNumbersRequired;
}
