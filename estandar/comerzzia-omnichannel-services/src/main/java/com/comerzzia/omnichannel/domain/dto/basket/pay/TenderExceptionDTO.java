package com.comerzzia.omnichannel.domain.dto.basket.pay;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TenderExceptionDTO {
	protected String paymentMethodCode;
	protected String exceptionType;
	protected String exceptionId;
	protected String message;
	
}
