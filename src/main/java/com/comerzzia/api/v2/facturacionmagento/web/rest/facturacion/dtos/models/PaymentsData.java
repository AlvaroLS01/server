package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentsData {

	private List<PaymentData> paymentData;

	public List<PaymentData> getPaymentData() {
		return paymentData;
	}

	public void setPaymentData(List<PaymentData> paymentData) {
		this.paymentData = paymentData;
	}
}
