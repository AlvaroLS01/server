package com.comerzzia.pos.services.payments.events;

import java.util.EventObject;

public class PaymentsCompletedEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7821707692663354279L;

	public PaymentsCompletedEvent(Object source) {
		super(source);
	}

}
