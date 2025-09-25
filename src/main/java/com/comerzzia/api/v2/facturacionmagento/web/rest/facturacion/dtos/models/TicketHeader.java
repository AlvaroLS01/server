package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketHeader {

	private String origenTicket;
	private String originalTicket;
	private String invoiceDocumentType;
	private String posId;

	@JsonProperty("audit_events")
	private AuditEvents auditEvents;

	public String getOrigenTicket() {
		return origenTicket;
	}

	public void setOrigenTicket(String origenTicket) {
		this.origenTicket = origenTicket;
	}

	public String getOriginalTicket() {
		return originalTicket;
	}

	public void setOriginalTicket(String originalTicket) {
		this.originalTicket = originalTicket;
	}

	public String getInvoiceDocumentType() {
		return invoiceDocumentType;
	}

	public void setInvoiceDocumentType(String invoiceDocumentType) {
		this.invoiceDocumentType = invoiceDocumentType;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public AuditEvents getAuditEvents() {
		return auditEvents;
	}

	public void setAuditEvents(AuditEvents auditEvents) {
		this.auditEvents = auditEvents;
	}

}
