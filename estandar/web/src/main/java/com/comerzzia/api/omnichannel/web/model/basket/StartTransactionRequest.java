package com.comerzzia.api.omnichannel.web.model.basket;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StartTransactionRequest {
	@Schema(nullable = true, example = "FS",  
			description = "Destination document type (FS/FT/NC/...). Default type in application settings")
	protected String documentType;
	@Schema(defaultValue = "false")
	protected Boolean trainingMode = false;
	@Schema(defaultValue = "false")
	protected Boolean scoMode = false;
	@Schema(defaultValue = "false")
	protected Boolean acceptDevolutions = false;
	
	protected PriceSettingsRequest priceSettings;
	protected LoyaltySettingsRequest loyaltySettings;
	
	@Schema(nullable = true, description = "Optional first item")
	protected BasketItemRequest startItem;
}
