package com.comerzzia.omnichannel.domain.dto.basket;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoyaltySettingsDTO {
	Long loyalCustomerId;
	
	List<String> collectives;
	
	List<String> tags;
	
	public Boolean isActive() {
		return (loyalCustomerId != null || 
				(collectives != null && collectives.size() > 0) ||
				(tags != null && tags.size() > 0));
	}	
}
