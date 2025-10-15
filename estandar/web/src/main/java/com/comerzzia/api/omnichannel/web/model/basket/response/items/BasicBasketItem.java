package com.comerzzia.api.omnichannel.web.model.basket.response.items;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.QueryParam;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasicBasketItem {
	@QueryParam("upc")
	@NotNull
	@Size(max = 50)
	protected String upc;
	protected String scanCodeType;
	protected Boolean scanned;
	
	protected String attendantId;
}
