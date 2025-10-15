package com.comerzzia.omnichannel.domain.dto.saledoc;


import com.comerzzia.omnichannel.domain.entity.saledoc.SaleDocPaymentEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SaleDocPaymentDTO extends SaleDocPaymentEntity {
	protected String paymentMethodCode;
	protected String paymentMethodDes;	
	protected Boolean cash;
	protected Boolean creditCard;
  
}
