package com.comerzzia.api.omnichannel.web.model.basket;

import java.math.BigDecimal;

import javax.ws.rs.QueryParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TenderRequest {
	@QueryParam("paymentMethodCode") 
	protected String paymentMethodCode;
	@QueryParam("upc") 
	protected String upc;
	@QueryParam("amount") 
	protected BigDecimal amount;
	@QueryParam("preAuthorizationMode") 
	protected Boolean preAuthorizationMode;
	@QueryParam("additionalData") 
	@Schema(type = "string", format = "byte")
	protected byte[] additionalData;
}
