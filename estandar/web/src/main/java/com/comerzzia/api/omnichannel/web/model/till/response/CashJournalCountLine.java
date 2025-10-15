package com.comerzzia.api.omnichannel.web.model.till.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CashJournalCountLine {
    private Integer lineId;
    
    private String paymentMethodCode;

    private Integer quantity;

    private BigDecimal countValue;    
}