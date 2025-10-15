package com.comerzzia.api.omnichannel.web.model.basket.response.pay;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TenderAccepted {
	protected Integer paymentId;
	protected String paymentMethodCode;
	protected String description;
	protected BigDecimal amount;

	protected Boolean enterByCashier;
	protected Boolean cancellable;
	
	protected String tillTransactionDetailId;
}
