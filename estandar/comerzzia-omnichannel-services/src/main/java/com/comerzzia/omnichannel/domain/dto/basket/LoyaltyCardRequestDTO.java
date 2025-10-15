package com.comerzzia.omnichannel.domain.dto.basket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoyaltyCardRequestDTO {
	protected String AccountNumber;
	protected String EntryMethod;
	protected String cardType;	
}
