package com.comerzzia.omnichannel.domain.dto.saledoc;

import com.comerzzia.omnichannel.domain.entity.saledoc.SaleDocTaxEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SaleDocTaxDTO extends SaleDocTaxEntity {
	protected java.math.BigDecimal percentage;
	protected java.math.BigDecimal surchargePercentage;
	protected String fiscalTaxTypeCode;
}
