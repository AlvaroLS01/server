package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoyaltyDetails {

	private String loyalCustomerId;

	public String getLoyalCustomerId() {
		return loyalCustomerId;
	}

	public void setLoyalCustomerId(String loyalCustomerId) {
		this.loyalCustomerId = loyalCustomerId;
	}
}
