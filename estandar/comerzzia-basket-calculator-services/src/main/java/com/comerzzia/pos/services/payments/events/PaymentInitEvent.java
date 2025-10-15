package com.comerzzia.pos.services.payments.events;

import java.util.EventObject;

public class PaymentInitEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4041393035677334567L;

	public PaymentInitEvent(Object source) {
		super(source);
	}

}
