package com.comerzzia.api.omnichannel.web.model.basket.response;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.comerzzia.api.omnichannel.web.model.basket.response.items.BasketItemSold;
import com.comerzzia.api.omnichannel.web.model.basket.response.pay.TenderAccepted;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasketTransactionResponse {
	protected String id;
	protected Long version;

	protected String destinationDocumentType;

	protected Date creationDate = new Date();
	protected Date lastUpdate;

	protected Boolean tenderMode = false;
	protected Boolean trainingMode = false;
	protected Boolean scoMode = false;
	protected Boolean acceptDevolutions = false;

	protected PriceSettings priceSettings;
	protected LoyaltySettings loyaltySettings;

	protected Totals totals = new Totals();

	protected HashMap<Integer, BasketItemSold> items = new HashMap<Integer, BasketItemSold>();

	protected HashMap<String, CouponApplied> couponsApplied;

	protected List<CouponIssue> couponsToIssue;

	protected List<DiscountSummary> discountsSummary;

	protected HashMap<Integer, TenderAccepted> payments;

	protected String tillTransactionId;
	protected String currentTillTransactionDetailId;
}
