package com.comerzzia.api.omnichannel.web.model.till.response;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CashJournalHdr {
    private String cashJournalUid; 
    private String storeCode;
    private String tillCode;
    private Date openingDate;
    private Date closingDate;
    private Date transmissionDate;
    private String openUserCode;
    private String closeUserCode; 
    private Date accountingDate;
}