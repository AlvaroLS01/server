package com.comerzzia.omnichannel.domain.entity.paytransactionline;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class PaymentTransactionLineEntity extends PaymentTransactionLineKey {
	private static final long serialVersionUID = 4153914096194342502L;

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
}