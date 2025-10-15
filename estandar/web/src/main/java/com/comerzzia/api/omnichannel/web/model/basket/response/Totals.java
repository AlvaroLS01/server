package com.comerzzia.api.omnichannel.web.model.basket.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Totals {
	protected BigDecimal totalAmount;
	protected BigDecimal taxAmount;
	protected BigDecimal balanceDue;
	protected Integer itemCount;
	protected BigDecimal discountAmount;
	protected Integer points;

	protected BigDecimal changeDue;
	
	protected String changePaymentMethodCode;	
}
