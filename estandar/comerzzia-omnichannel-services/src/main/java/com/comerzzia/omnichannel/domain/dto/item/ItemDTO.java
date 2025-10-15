package com.comerzzia.omnichannel.domain.dto.item;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.comerzzia.omnichannel.model.documents.sales.basket.items.IBasketItemData;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDTO implements IBasketItemData {
	protected String activityUid;
	
	protected String itemCode;
	protected String itemDes;
	protected String format;
	protected String taxTypeCode;
	protected Boolean combination1Active;
	protected Boolean combination2Active;
	protected String categoryCode;	
	protected String familyCode;
	protected String sectionCode;
	protected String supplierCode;
	protected String supplierReference;
	protected String manufacturerCode;
	protected String brandCode;	
	protected Boolean genericItem;
	protected Boolean weightRequired;
	protected Integer ageRequired;
	protected Boolean serialNumbersActive;
	
	protected Boolean active;
	protected Boolean featured;
	
	protected String comments;
	
    protected String unitMeasureCodeAlt; 
    protected String labelUnitMeasureCode;         
    protected BigDecimal LabelUnitMeasureQuantity;
    protected Date creationDate;              
    
    protected Integer scalePlu;                        
    protected String scaleSection;                
    protected String scaleItemType;               
    protected Long replacementTypeId;        
    protected String replacementTypeDes;
    
    protected Boolean confirmSalesPrice;
    
	protected String combination1Code;
	protected String combination2Code;
	
	protected Set<String> tags;
	protected Map<String, String> properties;
		
	// Additional barcode data
	protected String barcode;
	protected Boolean dun14;
	protected BigDecimal barcodeConversionFactor;
	protected Boolean mainBarcode;
	
}
