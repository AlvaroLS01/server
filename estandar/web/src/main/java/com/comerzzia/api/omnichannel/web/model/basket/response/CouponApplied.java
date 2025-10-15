package com.comerzzia.api.omnichannel.web.model.basket.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class CouponApplied extends Coupon { 
	protected BigDecimal discountAmount;
}
