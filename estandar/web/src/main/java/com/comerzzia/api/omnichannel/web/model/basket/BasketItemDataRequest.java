package com.comerzzia.api.omnichannel.web.model.basket;

import java.util.Map;
import java.util.Set;

import com.comerzzia.omnichannel.model.documents.sales.basket.items.IBasketItemData;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasketItemDataRequest implements IBasketItemData {
	protected String itemCode;
	protected String itemDes;
	protected String taxTypeCode;
	protected Boolean combination1Active;
	protected Boolean combination2Active;
	protected String categoryCode;	
	protected String familyCode;
	protected String sectionCode;
	protected String supplierCode;
	protected String brandCode;	
	
	protected Boolean genericItem;	
	protected Boolean weightRequired;
	protected Integer ageRequired;
	protected Boolean serialNumbersActive;

	protected String combination1Code;
	protected String combination2Code;
	
	protected Set<String> tags; 
	
	protected Map<String, String> properties;
}
