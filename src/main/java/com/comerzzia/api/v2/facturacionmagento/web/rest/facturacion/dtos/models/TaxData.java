package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxData {

	private BigDecimal baseAmount;
	private BigDecimal taxAmount;
	private BigDecimal taxPercentage;
	private BigDecimal taxSurchargePercentage;
	private String taxTypeCode;
	private BigDecimal total;

	public BigDecimal getBaseAmount() {
		return baseAmount;
	}

	public void setBaseAmount(BigDecimal baseAmount) {
		this.baseAmount = baseAmount;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public BigDecimal getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(BigDecimal taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	public BigDecimal getTaxSurchargePercentage() {
		return taxSurchargePercentage;
	}

	public void setTaxSurchargePercentage(BigDecimal taxSurchargePercentage) {
		this.taxSurchargePercentage = taxSurchargePercentage;
	}

	public String getTaxTypeCode() {
		return taxTypeCode;
	}

	public void setTaxTypeCode(String taxTypeCode) {
		this.taxTypeCode = taxTypeCode;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}
