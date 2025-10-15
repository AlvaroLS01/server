package com.comerzzia.pos.services.payments.events;

import java.util.EventObject;

public class PaymentsInitEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4041393035677334567L;

	private PaymentInitEvent initEvent;

	public PaymentsInitEvent(Object source, PaymentInitEvent initEvent) {
		super(source);
		this.initEvent = initEvent;
	}

	public PaymentInitEvent getInitEvent() {
		return initEvent;
	}

	public void setInitEvent(PaymentInitEvent initEvent) {
		this.initEvent = initEvent;
	}

}
