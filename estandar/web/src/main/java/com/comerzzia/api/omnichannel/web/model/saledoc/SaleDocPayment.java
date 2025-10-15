package com.comerzzia.api.omnichannel.web.model.saledoc;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleDocPayment {
	protected Integer lineId;
	protected Long paymentMethodId;
	
	protected String paymentMethodCode;
	protected String paymentMethodDes;	
	protected Boolean cash;
	protected Boolean creditCard;
	
	protected java.math.BigDecimal grossAmount;
	protected String transactionNumber;
}
