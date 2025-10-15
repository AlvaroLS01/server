package com.comerzzia.omnichannel.domain.dto.basket.items.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CouponExceptionDTO {
	protected String upc;
	protected String exceptionType;
	protected String exceptionId;
}
