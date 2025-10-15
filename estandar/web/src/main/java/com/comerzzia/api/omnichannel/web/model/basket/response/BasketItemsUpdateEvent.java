package com.comerzzia.api.omnichannel.web.model.basket.response;

import java.util.List;

import com.comerzzia.api.omnichannel.web.model.basket.response.items.BasketItemException;
import com.comerzzia.api.omnichannel.web.model.basket.response.items.BasketItemSold;
import com.comerzzia.api.omnichannel.web.model.basket.response.items.BasketItemVoided;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasketItemsUpdateEvent {
   protected BasketItemSold lastItem;
   
   protected List<BasketItemSold> itemsAffected;
   protected List<BasketItemVoided> itemsVoided;
      
   protected BasketItemException itemException;
   
   protected LoyaltyCard loyaltyCard;
   protected LoyaltyCardException loyaltyCardException;
   
   protected Totals totals;
}
