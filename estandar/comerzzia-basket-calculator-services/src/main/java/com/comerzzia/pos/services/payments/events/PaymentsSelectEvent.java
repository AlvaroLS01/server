package com.comerzzia.pos.services.payments.events;

import java.util.EventObject;

public class PaymentsSelectEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4041393035677334567L;

	private PaymentSelectEvent eventSelect;

	public PaymentsSelectEvent(Object source, PaymentSelectEvent eventSelect) {
		super(source);
		this.eventSelect = eventSelect;
	}

	public PaymentSelectEvent getEventSelect() {
		return eventSelect;
	}

	public void setEventSelect(PaymentSelectEvent eventSelect) {
		this.eventSelect = eventSelect;
	}

}
