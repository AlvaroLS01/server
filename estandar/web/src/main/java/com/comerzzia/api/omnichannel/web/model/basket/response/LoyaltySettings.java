package com.comerzzia.api.omnichannel.web.model.basket.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoyaltySettings {
	Long loyalCustomerId;
	
	List<String> collectives;
	
	List<String> tags;
}
