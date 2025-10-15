package com.comerzzia.pos.persistence.cajas;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class CashJournalHdr extends CashJournalHdrKey {
    protected String storeCode;

    protected String tillCode;

    protected Date openingDate;

    protected Date closingDate;

    protected Date transmissionDate;

    protected String openUserCode;

    protected String closeUserCode;
    
    protected Date accountingDate;  
}