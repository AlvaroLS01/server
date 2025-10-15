package com.comerzzia.pos.services.payments.events;

import java.util.EventObject;

public class PaymentSelectEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4041393035677334567L;

	public PaymentSelectEvent(Object source) {
		super(source);
	}

}
