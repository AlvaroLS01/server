package com.comerzzia.omnichannel.domain.entity.taxpercentage;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxPercentageEntityKey {
   private String activityUid;
   private Integer taxGroupId;
   private Long taxTreatmentId;
   private String taxTypeCode;
}
