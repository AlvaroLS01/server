package com.comerzzia.api.omnichannel.web.model.basket.response.pay;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentTransactionLineResponse  {
	protected String payTransactionLineUid;
	protected String payTransactionUid;
	protected Short lineId;
	protected String paymentMethodCode;
	protected BigDecimal amount;
	protected String currencyCode;	
	protected String transactionData;
	protected String paymentGatewayResponse;
	
	protected Date startDate;
	protected Date endDate;
	
	protected Short statusId;
	
	protected BasketPaymentsStatus paymentsStatus;
}