package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ticket {

	private TicketHeader ticketHeader;
	private TicketIssueData ticketIssueData;
	private TicketItems ticketItems;

	public TicketHeader getTicketHeader() {
		return ticketHeader;
	}

	public void setTicketHeader(TicketHeader ticketHeader) {
		this.ticketHeader = ticketHeader;
	}

	public TicketIssueData getTicketIssueData() {
		return ticketIssueData;
	}

	public void setTicketIssueData(TicketIssueData ticketIssueData) {
		this.ticketIssueData = ticketIssueData;
	}

	public TicketItems getTicketItems() {
		return ticketItems;
	}

	public void setTicketItems(TicketItems ticketItems) {
		this.ticketItems = ticketItems;
	}
}
