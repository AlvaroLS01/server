package com.comerzzia.api.omnichannel.web.model.till.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CashJournalTotals {
	protected BigDecimal totalSalesInput;
	protected BigDecimal totalSalesOutput;
	protected BigDecimal totalMovementsInput;
	protected BigDecimal totalMovementsOutput;
	protected BigDecimal totalCount;
}
