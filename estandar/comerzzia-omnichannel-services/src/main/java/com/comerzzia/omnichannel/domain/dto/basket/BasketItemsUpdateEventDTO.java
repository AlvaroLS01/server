package com.comerzzia.omnichannel.domain.dto.basket;

import java.util.ArrayList;
import java.util.List;

import com.comerzzia.omnichannel.domain.dto.basket.items.BasketItemSoldDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.BasketItemVoidedDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.exception.ItemExceptionDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasketItemsUpdateEventDTO {
   protected BasketItemSoldDTO lastItem;
   
   protected List<BasketItemSoldDTO> itemsAffected;
   protected List<BasketItemVoidedDTO> itemsVoided;
      
   protected ItemExceptionDTO itemException;
   
   protected LoyaltyCardDTO loyaltyCard;
   protected LoyaltyCardExceptionDTO loyaltyCardException;
   
   protected TotalsDTO totals;
   
   public BasketItemsUpdateEventDTO(final BasketTransactionDTO basketTransaction) {
      this.totals = basketTransaction.getTotals();
   }   
      
   public void setItemsAffected(List<BasketItemSoldDTO> itemsAffected) {
	   if (itemsAffected == null) return;
	   
	   for (BasketItemSoldDTO itemSold : itemsAffected) {
		   addItemAffected(itemSold);
	   }
   }
   
   public void addItemAffected(final BasketItemSoldDTO itemSold) {
      if (itemsAffected == null) {
         itemsAffected = new ArrayList<BasketItemSoldDTO>();
      }
      
      if ((lastItem != null) && (lastItem.getItemNumber().compareTo(itemSold.getItemNumber()) == 0)) {
    	 lastItem = itemSold;  
      } else {      
         itemsAffected.add(itemSold);
      }
   }
   
   public void addItemVoided(final BasketItemVoidedDTO itemVoided) {
      if (itemsVoided == null) {
         itemsVoided = new ArrayList<BasketItemVoidedDTO>();
      }
      
      itemsVoided.add(itemVoided);
   }
}
