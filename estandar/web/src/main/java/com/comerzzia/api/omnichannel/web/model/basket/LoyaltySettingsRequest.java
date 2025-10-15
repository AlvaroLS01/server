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
@Schema(description = "Optional loyalty settings for promotions calculation")
public class LoyaltySettingsRequest {
	@QueryParam("loyalCustomerId")
	protected Long loyalCustomerId;
	
	@QueryParam("collectives") 
	protected List<String> collectives;
	
	@QueryParam("tags") 
	protected List<String> tags;
}
