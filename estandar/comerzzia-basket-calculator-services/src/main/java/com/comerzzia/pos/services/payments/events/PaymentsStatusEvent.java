package com.comerzzia.pos.services.payments.events;

import java.util.EventObject;

public class PaymentsStatusEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 709352095202245893L;

	private PaymentStatusEvent statusEvent;

	public PaymentsStatusEvent(Object source, PaymentStatusEvent statusEvent) {
		super(source);
		this.statusEvent = statusEvent;
	}

	public PaymentStatusEvent getStatusEvent() {
		return statusEvent;
	}

	public void setStatusEvent(PaymentStatusEvent statusEvent) {
		this.statusEvent = statusEvent;
	}

}
