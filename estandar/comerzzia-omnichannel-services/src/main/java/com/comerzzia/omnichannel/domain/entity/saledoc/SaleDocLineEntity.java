package com.comerzzia.omnichannel.domain.entity.saledoc;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleDocLineEntity {
   private String activityUid;
   private Long salesDocId;
   private Integer lineId;
   private java.util.Date salesDocDate;
   private String itemCode;
   private String combination1Code;
   private String combination2Code;
   private String itemDes;
   private String expandedDes;
   private String unitMeasureCode;
   private java.math.BigDecimal unitMeasureQuantity;
   private java.math.BigDecimal quantity;
   private java.math.BigDecimal salesPrice;
   private java.math.BigDecimal salesPriceWithTaxes;
   private java.math.BigDecimal discount;
   private java.math.BigDecimal grossAmount;
   private java.math.BigDecimal grandAmount;
   private String taxTypeCode;
   private java.math.BigDecimal unitCostPrice;
   private Long salesOrderId;
   private Integer orderLineId;
   private String lineType;
   private Long promotionId;
   private String auxCashier;
   private Long linkSalesDocId;
   private Integer linkSalesDocLineId;
   private Integer stockOperation;
}
