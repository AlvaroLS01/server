package com.comerzzia.omnichannel.domain.dto.basket.items.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasketItemVoidRequestDTO {
	protected String upc; 
	protected Integer itemNumber;
}
