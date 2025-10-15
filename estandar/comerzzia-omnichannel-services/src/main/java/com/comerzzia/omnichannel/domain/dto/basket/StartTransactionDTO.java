package com.comerzzia.omnichannel.domain.dto.basket;

import com.comerzzia.omnichannel.domain.dto.basket.items.request.BasketItemRequestDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StartTransactionDTO {
	protected String documentType;
	protected Boolean trainingMode = false;
	protected Boolean scoMode = false;
	protected Boolean acceptDevolutions = false;
	
	protected PriceSettingsDTO priceSettings;
	protected LoyaltySettingsDTO loyaltySettings;

	protected BasketItemRequestDTO startItem;
}
