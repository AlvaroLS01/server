package com.comerzzia.omnichannel.domain.dto.basket.pay;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TenderDTO {
	protected String paymentMethodCode;
	protected String upc;
	protected BigDecimal amount;
	protected Boolean preAuthorizationMode;
	protected byte[] additionalData;
}
