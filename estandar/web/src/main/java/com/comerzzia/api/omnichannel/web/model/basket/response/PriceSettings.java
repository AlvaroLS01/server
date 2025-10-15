package com.comerzzia.api.omnichannel.web.model.basket.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceSettings {
	List<String> rates;
	
	String referenceRateCode;
}
