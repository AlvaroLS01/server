package com.comerzzia.omnichannel.domain.entity.paytransactionline;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransactionLineKey implements Serializable {
	private static final long serialVersionUID = 7300688462274710017L;

	private String activityId;	
    private String payTransactionLineUid;
}