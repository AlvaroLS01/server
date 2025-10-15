package com.comerzzia.pos.services.giftcard;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.services.core.sesion.Sesion;
import com.comerzzia.pos.services.payments.configuration.PaymentMethodConfiguration;

@Component
public class GiftCardService {
	@Autowired
	Sesion sesion;
	
	public boolean isGiftCardItem(String codArticulo) {
		for(PaymentMethodConfiguration paymentMehtodConfiguration : sesion.getSesionCaja().getPaymentMethodsData().getPaymentMethodConfigurations()) {
			String itemCodes = paymentMehtodConfiguration.getConfigurationProperty("articulos_gift_card");
			if(StringUtils.isNotBlank(itemCodes)) {
				String[] itemCodesSplit = itemCodes.split(",");
				for(int i = 0 ; i < itemCodesSplit.length ; i++) {
					String itemCodeGiftCard = itemCodesSplit[i];
					itemCodeGiftCard = itemCodeGiftCard.trim();
					if(itemCodeGiftCard.equals(codArticulo)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

}
