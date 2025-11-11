package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import java.math.BigDecimal;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketItems {

	private List<TicketItem> ticketItem;

	private BigDecimal totalQuantity;

	public List<TicketItem> getTicketItem() {
		return ticketItem;
	}

	public void setTicketItem(List<TicketItem> ticketItem) {
		this.ticketItem = ticketItem;
	}

	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

}
