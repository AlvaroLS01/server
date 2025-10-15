package com.comerzzia.api.omnichannel.web.model.till.response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CashJournalLine  {
    private Integer lineId;    
    private Date cashJournalDate;
    private BigDecimal input;
    private BigDecimal output;
    private String concept;
    private String salesDocCode;
    private String paymentMethodCode;
    private String salesDocUid;    
    private String movConceptCode;
    private Long docTypeId;
    private String patyTransactionLineUid;
    private String currencyCode;
    private BigDecimal changeType;
    private String userCode;    
}