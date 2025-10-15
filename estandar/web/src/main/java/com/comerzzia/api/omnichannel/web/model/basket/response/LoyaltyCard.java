package com.comerzzia.api.omnichannel.web.model.basket.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoyaltyCard {
	protected String AccountNumber;
	protected String EntryMethod;
	protected String cardType;

	protected String CountryCode;

	protected String FirstName;
	protected String LastName;

	protected String collectivesList;
	protected String tagsList;

	protected BigDecimal points;
	protected BigDecimal amount;
	protected Boolean paperLess;

	protected String message;
	protected String marketingMessage;
	
	protected List<Coupon> avaliableCoupons;
}
