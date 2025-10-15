package com.comerzzia.api.omnichannel.web.model.till.response;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CashJournalResponse {
	protected CashJournalHdr cashJournalHdr;
	
	protected CashJournalTotals cashJournalTotals;
	
	protected List<CashJournalCountLine> cashJournalCountLines;

	protected List<CashJournalLine> cashJournalLines;	
}
