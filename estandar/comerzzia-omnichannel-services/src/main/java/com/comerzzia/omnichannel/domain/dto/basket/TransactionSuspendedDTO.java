package com.comerzzia.omnichannel.domain.dto.basket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionSuspendedDTO {
	protected String id;
	protected String message;
}
