package com.comerzzia.omnichannel.domain.entity.saledoc;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleDocPaymentEntity {
   private String activityUid;
   private Long salesDocId;
   private Integer lineId;
   private Long paymentMethodId;
   private java.math.BigDecimal grossAmount;
   private String transactionNumber;
}
