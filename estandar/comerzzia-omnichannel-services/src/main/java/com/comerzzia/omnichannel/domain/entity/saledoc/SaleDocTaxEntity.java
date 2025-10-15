package com.comerzzia.omnichannel.domain.entity.saledoc;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleDocTaxEntity {
   private String activityUid;
   private Long salesDocId;
   private String taxTypeCode;
   private java.math.BigDecimal baseAmount;
   private java.math.BigDecimal taxAmount;
   private java.math.BigDecimal total;
}
