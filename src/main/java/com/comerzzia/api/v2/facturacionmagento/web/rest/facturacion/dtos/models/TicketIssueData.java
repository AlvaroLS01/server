package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketIssueData {

	private String invoiceCurrencyCode;
        private String issueDate;
        private String origenIssueDate;
        private String fechaTicketOrigen;
        private TaxesData taxesData;
        private BigDecimal totalBaseAmount;
        private BigDecimal totalTaxAmount;
        private BigDecimal totalGrossAmount;

	public String getInvoiceCurrencyCode() {
		return invoiceCurrencyCode;
	}

	public void setInvoiceCurrencyCode(String invoiceCurrencyCode) {
		this.invoiceCurrencyCode = invoiceCurrencyCode;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getOrigenIssueDate() {
		return origenIssueDate;
	}

        public void setOrigenIssueDate(String origenIssueDate) {
                this.origenIssueDate = origenIssueDate;
        }

        public String getFechaTicketOrigen() {
                return fechaTicketOrigen;
        }

        public void setFechaTicketOrigen(String fechaTicketOrigen) {
                this.fechaTicketOrigen = fechaTicketOrigen;
        }

        public TaxesData getTaxesData() {
                return taxesData;
        }

	public void setTaxesData(TaxesData taxesData) {
		this.taxesData = taxesData;
	}

	public BigDecimal getTotalBaseAmount() {
		return totalBaseAmount;
	}

	public void setTotalBaseAmount(BigDecimal totalBaseAmount) {
		this.totalBaseAmount = totalBaseAmount;
	}

	public BigDecimal getTotalTaxAmount() {
		return totalTaxAmount;
	}

	public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
		this.totalTaxAmount = totalTaxAmount;
	}

	public BigDecimal getTotalGrossAmount() {
		return totalGrossAmount;
	}

	public void setTotalGrossAmount(BigDecimal totalGrossAmount) {
		this.totalGrossAmount = totalGrossAmount;
	}
}
