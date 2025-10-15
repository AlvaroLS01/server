package com.comerzzia.omnichannel.domain.dto.basket;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceSettingsDTO {
	List<String> rates;
	
	String referenceRateCode;
	
	public Boolean isActive() {
		return rates != null && rates.size() > 0;
	}
}
