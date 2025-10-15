package com.comerzzia.api.omnichannel.web.model.saledoc;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleDocLinePriceMod {
	protected Integer updateLineId;
	protected String reasonCode;
	protected String reasonDoc;
	protected java.math.BigDecimal inputPrice;
	protected java.math.BigDecimal outputPrice;
	protected java.math.BigDecimal priceModificationAmount;
	protected String appliedTo;
	protected Long userId;
}
