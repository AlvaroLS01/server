package com.comerzzia.api.omnichannel.web.model.basket.response;

import com.comerzzia.api.omnichannel.web.model.basket.response.items.BasketItemException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class LoyaltyCardException extends BasketItemException {
	protected String accountNumber;
	protected String entryMethod;
	protected String cardType;	
	protected String status;
}
