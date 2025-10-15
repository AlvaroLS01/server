package com.comerzzia.api.omnichannel.web.model.basket.response.pay;

import java.util.Map;

import com.comerzzia.api.omnichannel.web.model.basket.response.Totals;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BasketPaymentsStatus {

	@JsonProperty("totals")
	private Totals totals;

	@JsonProperty("payments")
	private Map<String, TenderAccepted> payments;

	@JsonProperty("tillTransactionId")
	private String tillTransactionId;

	@JsonProperty("currentTillTransactionDetailId")
	private String currentTillTransactionDetailId;

}
