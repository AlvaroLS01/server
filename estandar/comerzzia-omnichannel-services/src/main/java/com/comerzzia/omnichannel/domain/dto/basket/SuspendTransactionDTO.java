package com.comerzzia.omnichannel.domain.dto.basket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SuspendTransactionDTO implements PermissionOperation {
   protected final String operationCode = "SUSPEND_SALE";
   
	protected String id;
}
