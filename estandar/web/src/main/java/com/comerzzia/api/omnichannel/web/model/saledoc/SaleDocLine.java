package com.comerzzia.api.omnichannel.web.model.saledoc;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleDocLine {
	protected Integer lineId;
	protected String itemCode;
	protected String combination1Code;
	protected String combination2Code;
	protected String itemDes;
	protected String expandedDes;
	protected String unitMeasureCode;
	protected java.math.BigDecimal unitMeasureQuantity;
	protected java.math.BigDecimal quantity;
	protected java.math.BigDecimal salesPrice;
	protected java.math.BigDecimal salesPriceWithTaxes;
	protected java.math.BigDecimal discount;
	protected java.math.BigDecimal grossAmount;
	protected java.math.BigDecimal grandAmount;
	protected String taxTypeCode;
	protected java.math.BigDecimal unitCostPrice;
	protected Long salesOrderId;
	protected Integer orderLineId;
	protected String lineType;
	protected Long promotionId;
	protected String auxCashier;
	protected Long linkSalesDocId;
	protected Integer linkSalesDocLineId;
	protected Integer stockOperation;

	protected List<SaleDocLinePriceMod> priceModifications;
}
