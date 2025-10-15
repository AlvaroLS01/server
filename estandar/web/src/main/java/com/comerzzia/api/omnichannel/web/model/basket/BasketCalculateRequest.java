package com.comerzzia.api.omnichannel.web.model.basket;

import java.util.List;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasketCalculateRequest {	
	protected PriceSettingsRequest priceSettings;
	protected LoyaltySettingsRequest loyaltySettings;
	
	@ArraySchema(minItems = 1, schema = @Schema(required = true, example = "[{\"upc\" : \"1\"}, {\"upc\" : \"2\"}, {\"upc\" : \"3\"}]"))
	protected List<BasketItemRequest> items;
	protected List<CouponRequest> coupons;
}
