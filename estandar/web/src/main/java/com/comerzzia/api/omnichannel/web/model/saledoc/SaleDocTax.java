package com.comerzzia.api.omnichannel.web.model.saledoc;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleDocTax {
	protected String taxTypeCode;
	protected java.math.BigDecimal percentage;
	protected java.math.BigDecimal surchargePercentage;
	protected String fiscalTaxTypeCode;
	
	protected java.math.BigDecimal baseAmount;
	protected java.math.BigDecimal taxAmount;
	protected java.math.BigDecimal total;
	
}
