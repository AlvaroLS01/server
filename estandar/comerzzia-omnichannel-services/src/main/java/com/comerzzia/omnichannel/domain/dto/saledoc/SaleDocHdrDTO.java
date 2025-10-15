package com.comerzzia.omnichannel.domain.dto.saledoc;


import java.util.List;

import com.comerzzia.omnichannel.domain.entity.saledoc.SaleDocHdrEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SaleDocHdrDTO extends SaleDocHdrEntity {
	protected String docTypeCode;
	protected String docTypeDes;
	
	protected List<SaleDocLineDTO> lines;

	protected List<SaleDocTaxDTO> taxes;
	
	protected List<SaleDocPaymentDTO> payments;
}
