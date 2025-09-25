package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketHeader {

        private String origenTicket;
        private String originalTicket;
        private String invoiceDocumentType;
        private String posId;

        @Schema(description = "Datos manuales del ticket origen. Se utilizarán cuando no se localice el UID indicado en originalTicket", implementation = DocumentOriginData.class)
        private DocumentOriginData documentOriginData;

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

        public DocumentOriginData getDocumentOriginData() {
                return documentOriginData;
        }

        public void setDocumentOriginData(DocumentOriginData documentOriginData) {
                this.documentOriginData = documentOriginData;
        }

        public AuditEvents getAuditEvents() {
                return auditEvents;
        }

        public void setAuditEvents(AuditEvents auditEvents) {
		this.auditEvents = auditEvents;
	}

}
