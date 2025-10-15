package com.comerzzia.api.omnichannel.web.model.basket.response.pay;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Tender {
	protected String paymentMethodCode;	
	protected String upc;
	protected BigDecimal amount;
	protected Boolean preAuthorizationMode;
	@Schema(type = "string", format = "byte")
	protected byte[] additionalData;
}
