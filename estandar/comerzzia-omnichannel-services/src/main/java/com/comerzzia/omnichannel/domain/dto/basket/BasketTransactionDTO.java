package com.comerzzia.omnichannel.domain.dto.basket;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.comerzzia.omnichannel.domain.dto.basket.items.BasketItemSoldDTO;
import com.comerzzia.omnichannel.domain.dto.basket.pay.TenderAcceptedDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasketTransactionDTO {
	protected String id;
	protected Long version;

	protected String destinationDocumentType;

	protected Date creationDate = new Date();
	protected Date lastUpdate;

	protected LoyaltyCardDTO loyaltyCard;

	protected Boolean tenderMode = false;
	protected Boolean trainingMode = false;
	protected Boolean scoMode = false;
	protected Boolean acceptDevolutions = false;

	protected PriceSettingsDTO priceSettings;
	protected LoyaltySettingsDTO loyaltySettings;

	protected TotalsDTO totals = new TotalsDTO();

	protected HashMap<Integer, BasketItemSoldDTO> items = new HashMap<Integer, BasketItemSoldDTO>();

	protected HashMap<String, CouponAppliedDTO> couponsApplied;

	protected List<CouponIssueDTO> couponsToIssue;

	protected List<DiscountSummaryDTO> discountsSummary;

	protected HashMap<Integer, TenderAcceptedDTO> payments;

	protected String tillTransactionId;
	protected String currentTillTransactionDetailId;
}
