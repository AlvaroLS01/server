package com.comerzzia.omnichannel.domain.dto.basket.items;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasketItemVoidedDTO {
   protected Integer itemNumber;   
	protected String upc;
	protected String message;	
}
