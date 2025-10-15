package com.comerzzia.api.omnichannel.web.model.till.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentMethod {
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
