package com.comerzzia.omnichannel.domain.entity.item;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemEntity {
	protected String activityUid;
	protected String itemCode;
	protected String itemDes;
	protected String format;
	protected String familyCode;
	protected String sectionCode;
	protected String categoryCode;
	protected String supplierCode;
	protected String supplierReference;
	protected String manufacturerCode;
	protected String taxTypeCode;
	protected String comments;
	protected Boolean active;
	protected Boolean serialNumbersActive;
	protected Boolean combination1Active;
	protected Boolean combination2Active;
	protected Boolean genericItem;
	protected Boolean featured;
	protected String unitMeasureCodeAlt;
	protected String labelUnitMeasureCode;
	protected java.math.BigDecimal labelUnitMeasureQuantity;
	protected java.util.Date creationDate;
	protected Long version;
	protected java.util.Date versionDate;
	protected String brandCode;
	protected Integer scalePlu;
	protected String scaleSection;
	protected String scaleItemType;
	protected Integer replacementTypeId;
	protected Boolean confirmSalesPrice;
}
