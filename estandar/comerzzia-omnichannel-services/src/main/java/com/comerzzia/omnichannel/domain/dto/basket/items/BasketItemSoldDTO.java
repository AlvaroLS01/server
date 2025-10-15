package com.comerzzia.omnichannel.domain.dto.basket.items;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

import com.comerzzia.omnichannel.domain.dto.basket.items.request.BasketItemRequestDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BasketItemSoldDTO extends BasketItemRequestDTO{
   @XmlAttribute
   protected Integer itemNumber;
   protected Integer associatedItemNumber = 0;

   protected String internalCode;   
   protected String combination1Code;
   protected String combination2Code;
   
	protected String description;
	protected String largeDescription;

	//Price * Quantity
	protected BigDecimal extendedPrice;
		
	protected Boolean manualPrice;
	protected Boolean manualDiscount;
	
	//protected BigDecimal discountPercentage;
	protected BigDecimal discountAmount;
	protected String discountDescription;
	
	protected String itemStyle;
	protected String discountStyle;
	
	protected BasketItemDataDTO itemData;
	
	protected List<BasketItemDiscountDetailDTO> discountList;
	protected List<BasketItemDiscountDetailDTO> potentialDiscounts;
}
