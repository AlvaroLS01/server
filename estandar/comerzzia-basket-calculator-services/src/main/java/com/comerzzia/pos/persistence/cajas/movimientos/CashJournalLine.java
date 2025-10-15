package com.comerzzia.pos.persistence.cajas.movimientos;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class CashJournalLine extends CashJournalLineKey {
    protected Date cashJournalDate;

    protected BigDecimal output;

    protected BigDecimal input;

    protected String concept;

    protected String salesDocCode;

    protected String paymentMethodCode;

    protected String salesDocUid;
    
    protected String movConceptCode;

    protected Long docTypeId;

    protected String payTransactionLineUid;

    protected String currencyCode;

    protected BigDecimal changeType;

    protected String userCode;

}