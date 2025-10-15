package com.comerzzia.omnichannel.domain.dto.paymentmethod;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentMethodDTO {
    protected String paymentMethodCode;
    protected String paymentMethodDes;
    protected Boolean defaultMethod;
    
    protected Boolean cash;
    protected Boolean creditCard;
    protected Boolean credit;
    
    protected Boolean available;
    protected Boolean availableForReturn;
    protected Boolean availableForExchange;
    
    protected Boolean uniquePayPermited;
}
