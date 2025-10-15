package com.comerzzia.omnichannel.domain.dto.basket.items;

import java.util.Map;
import java.util.Set;

import com.comerzzia.omnichannel.model.documents.sales.basket.items.IBasketItemData;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasketItemDataDTO implements IBasketItemData {
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
		
	protected Set<String> tags;
	protected Map<String, String> properties;

}
