package com.comerzzia.api.omnichannel.web.model.basket.response.items;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasketItemDiscountDetail {
	protected String origin;
    protected String upc;
    
    protected String description;
    
    protected Long discountType;
    protected Long discountDestination;

    protected BigDecimal quantity;
    protected BigDecimal discountQuantity;
    
    protected BigDecimal amount;
    protected Integer pointsObtained;
    
    protected String imageUrl;
    protected boolean exclusive;
}
