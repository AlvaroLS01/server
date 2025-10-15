package com.comerzzia.api.omnichannel.web.model.basket.response;

import java.util.HashMap;
import java.util.List;

import com.comerzzia.api.omnichannel.web.model.basket.response.items.BasketItemException;
import com.comerzzia.api.omnichannel.web.model.basket.response.items.BasketItemSold;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasketCalculateResponse {
	protected PriceSettings priceSettings;
	protected LoyaltySettings loyaltySettings;

	protected Totals totals = new Totals();

	protected HashMap<Integer, BasketItemSold> items = new HashMap<Integer, BasketItemSold>();

	protected HashMap<String, CouponApplied> couponsApplied;

	protected List<CouponIssue> couponsToIssue;

	protected List<DiscountSummary> discountsSummary;
	
	protected List<BasketItemException> itemsExceptions;
}
