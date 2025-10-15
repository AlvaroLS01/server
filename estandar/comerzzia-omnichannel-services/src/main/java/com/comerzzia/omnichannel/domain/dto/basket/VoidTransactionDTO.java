package com.comerzzia.omnichannel.domain.dto.basket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoidTransactionDTO implements PermissionOperation  {
   protected final String operationCode = "VOID_SALE";
   
	protected String attendantId;
}
