package com.comerzzia.pos.services.payments.events;

import java.util.EventObject;

public class PaymentStatusEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 709352095202245893L;

	public PaymentStatusEvent(Object source, String code, String message) {
		super(source);
		this.code = code;
		this.message = message;
	}

	private String code;

	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
