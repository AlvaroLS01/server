package com.comerzzia.api.omnichannel.web.model.basket;

import java.util.List;

import javax.ws.rs.QueryParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Optional price rates preferences. If null, default values from store configuration")
public class PriceSettingsRequest {
	@QueryParam("rates")
	List<String> rates;
	
	@QueryParam("referenceRateCode")
	String referenceRateCode;
}
