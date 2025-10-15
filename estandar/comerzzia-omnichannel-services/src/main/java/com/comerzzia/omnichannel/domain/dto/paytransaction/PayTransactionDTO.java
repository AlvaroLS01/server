package com.comerzzia.omnichannel.domain.dto.paytransaction;

import java.math.BigDecimal;
import java.util.List;

import com.comerzzia.omnichannel.domain.entity.paytransaction.PayTransactionEntity;
import com.comerzzia.omnichannel.domain.entity.paytransactionline.PaymentTransactionLineEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PayTransactionDTO extends PayTransactionEntity {
	protected BigDecimal balance;
	
	protected List<PaymentTransactionLineEntity> details;
}
