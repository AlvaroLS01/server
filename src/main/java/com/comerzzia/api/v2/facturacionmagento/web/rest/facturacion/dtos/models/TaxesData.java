package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxesData {

	private List<TaxData> taxData;

	public List<TaxData> getTaxData() {
		return taxData;
	}

	public void setTaxData(List<TaxData> taxData) {
		this.taxData = taxData;
	}
}
