package com.comerzzia.api.omnichannel.web.model.basket.response.items;

import javax.xml.bind.annotation.XmlSeeAlso;

import com.comerzzia.api.omnichannel.web.model.basket.response.LoyaltyCardException;

import lombok.Data;

@Data
@XmlSeeAlso({ BasketItemSalesException.class, LoyaltyCardException.class })
public class BasketItemException {
	protected String upc;
	protected String itemType;
	protected Boolean notFound;
	protected Boolean inactive;

	protected String message;
}
