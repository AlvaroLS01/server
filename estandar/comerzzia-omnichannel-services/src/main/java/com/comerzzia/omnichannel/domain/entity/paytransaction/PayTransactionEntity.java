package com.comerzzia.omnichannel.domain.entity.paytransaction;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class PayTransactionEntity extends PayTransactionKey {	
	protected String posUid;	
	protected String cashJournalUid;	
	protected String salesDocUid;	
	protected Date payTransactionDate;
	protected Date startDate;	
    protected Date endDate;	
	protected BigDecimal amount;	
	protected String currencyCode;	
	protected Short statusId;   	
	protected String payTransactionUidLink;
}