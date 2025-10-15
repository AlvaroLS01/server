package com.comerzzia.api.omnichannel.web.model.basket.response.items;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasketItemVoided {
   protected Integer itemNumber;   
	protected String upc;
	protected String message;	
}
