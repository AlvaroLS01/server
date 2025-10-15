package com.comerzzia.omnichannel.domain.entity.saledoc;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleDocLinePriceModEntity {
   private String activityUid;
   private String salesDocUid;
   private Integer lineId;
   private Integer updateLineId;
   private String reasonCode;
   private String reasonDoc;
   private java.math.BigDecimal inputPrice;
   private java.math.BigDecimal outputPrice;
   private java.math.BigDecimal priceModificationAmount;
   private String appliedTo;
   private Long userId;
}
