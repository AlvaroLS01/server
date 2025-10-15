package com.comerzzia.omnichannel.domain.dto.basket.pay;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TenderAcceptedDTO {
	protected Integer paymentId;
	protected String paymentMethodCode;
	protected String description;
	protected BigDecimal amount;

	protected boolean enterByCashier;
	protected boolean cancellable;
	
	protected String tillTransactionDetailId;
}
