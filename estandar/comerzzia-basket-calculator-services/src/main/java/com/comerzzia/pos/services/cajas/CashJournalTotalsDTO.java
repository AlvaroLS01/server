package com.comerzzia.pos.services.cajas;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CashJournalTotalsDTO {
	protected BigDecimal totalSalesInput = BigDecimal.ZERO;
	protected BigDecimal totalSalesOutput = BigDecimal.ZERO;
	protected BigDecimal totalMovementsInput = BigDecimal.ZERO;
	protected BigDecimal totalMovementsOutput = BigDecimal.ZERO;
	protected BigDecimal totalCount = BigDecimal.ZERO;
}
