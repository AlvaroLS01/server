package com.comerzzia.pos.services.payments.events;

import java.util.EventObject;

public class PaymentsErrorEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4685487546578270097L;

	private PaymentErrorEvent errorEvent;

	public PaymentsErrorEvent(Object source, PaymentErrorEvent errorEvent) {
		super(source);
		this.errorEvent = errorEvent;
	}

	public PaymentErrorEvent getErrorEvent() {
		return errorEvent;
	}

	public void setErrorEvent(PaymentErrorEvent errorEvent) {
		this.errorEvent = errorEvent;
	}

}
