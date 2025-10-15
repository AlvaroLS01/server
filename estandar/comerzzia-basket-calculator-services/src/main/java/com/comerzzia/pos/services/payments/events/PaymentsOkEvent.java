package com.comerzzia.pos.services.payments.events;

import java.util.EventObject;

public class PaymentsOkEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3660381588818399949L;

	private PaymentOkEvent okEvent;

	public PaymentsOkEvent(Object source, PaymentOkEvent okEvent) {
		super(source);
		this.okEvent = okEvent;
	}

	public PaymentOkEvent getOkEvent() {
		return okEvent;
	}

	public void setOkEvent(PaymentOkEvent okEvent) {
		this.okEvent = okEvent;
	}

}
