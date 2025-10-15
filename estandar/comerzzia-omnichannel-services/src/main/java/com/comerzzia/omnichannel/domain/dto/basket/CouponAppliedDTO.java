package com.comerzzia.omnichannel.domain.dto.basket;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class CouponAppliedDTO extends CouponDTO { 
	protected BigDecimal discountAmount;
}
