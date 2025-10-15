package com.comerzzia.api.omnichannel.web.model.basket;

import javax.ws.rs.QueryParam;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoidTransactionRequest {
	@QueryParam("attendantId")
	protected String attendantId;
}
