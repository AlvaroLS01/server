package com.comerzzia.omnichannel.domain.dto.saledoc;


import java.util.List;

import com.comerzzia.omnichannel.domain.entity.saledoc.SaleDocLineEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SaleDocLineDTO extends SaleDocLineEntity {
	protected List<SaleDocLinePriceModDTO> priceModifications;
}
