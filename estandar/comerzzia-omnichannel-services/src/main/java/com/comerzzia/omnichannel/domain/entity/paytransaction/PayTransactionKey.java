package com.comerzzia.omnichannel.domain.entity.paytransaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayTransactionKey {	
	private String activityId;	
    private String payTransactionUid;
}
