package com.comerzzia.omnichannel.domain.dto.basket.pay;

import java.util.HashMap;

import com.comerzzia.omnichannel.domain.dto.basket.TotalsDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentsStatusDto {
	
	  @JsonProperty("totals")
	  private TotalsDTO totals;
	  
	  @JsonProperty("payments")
	  protected HashMap<Integer, TenderAcceptedDTO> payments;

	  @JsonProperty("tillTransactionId")
	  private String tillTransactionId;

	  @JsonProperty("currentTillTransactionDetailId")
	  private String currentTillTransactionDetailId;
	
	

}
