package com.comerzzia.api.omnichannel.web.model.basket.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiscountSummary {
	protected String origin;
    protected String upc;
    
    protected String description;
    
    protected Long discountType;
    protected Long discountDestination;
    
    protected BigDecimal amount;
    protected Integer pointsObtained;
    
    protected String imageUrl;
    protected boolean exclusive;
}
