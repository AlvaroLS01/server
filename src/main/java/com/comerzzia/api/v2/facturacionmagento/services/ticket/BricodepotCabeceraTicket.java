package com.comerzzia.api.v2.facturacionmagento.services.ticket;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.services.ticket.audit.TicketAuditEvent;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.CabeceraTicket;

@XmlAccessorType(XmlAccessType.FIELD)
@Component
@Primary
@Scope("prototype")
public class BricodepotCabeceraTicket extends CabeceraTicket {

	@XmlElementWrapper(name = "eventos_auditoria")
	@XmlElement(name = "evento")
	protected List<TicketAuditEvent> auditEvents;

	public BricodepotCabeceraTicket(){
		auditEvents = new ArrayList<TicketAuditEvent>();
	}
	
	public List<TicketAuditEvent> getAuditEvents() {
		return auditEvents;
	}

	public void setAuditEvents(List<TicketAuditEvent> auditEvents) {
		this.auditEvents = auditEvents;
	}

	public void addAuditEvent(TicketAuditEvent auditEvent) {
		if (this.auditEvents == null) {
			this.auditEvents = new ArrayList<>();
		}
		this.auditEvents.add(auditEvent);
	}
}
