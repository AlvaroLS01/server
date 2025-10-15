package com.comerzzia.omnichannel.domain.entity.taxpercentage;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaxPercentageEntity  {
   private String activityUid;
   private Integer taxGroupId;
   private Long taxTreatmentId;
   private String taxTypeCode;
   private java.math.BigDecimal percentage;
   private java.math.BigDecimal surchargePercentage;
   private String fiscalTaxTypeCode;
}
