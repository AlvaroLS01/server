package com.comerzzia.omnichannel.domain.dto.basket.items.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ItemSalesExceptionDTO extends ItemExceptionDTO {
   private static final long serialVersionUID = -1980766460711696552L;

   protected Boolean recalled;
	
	protected Boolean timeRestricted;
	
	protected Boolean weightRequired;	
	
	protected Boolean quantityRequired;
	
	protected Boolean quantityRestricted;
	
	protected Boolean priceRequired;
	
	public ItemSalesExceptionDTO() {
	   this.itemType = "itemSale";
	}
}
