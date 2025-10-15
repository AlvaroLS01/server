package com.comerzzia.pos.persistence.cajas.movimientos;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CashJournalSummaryDTO {
	protected String docType;

	protected BigDecimal docCount;

	protected BigDecimal total;
}
