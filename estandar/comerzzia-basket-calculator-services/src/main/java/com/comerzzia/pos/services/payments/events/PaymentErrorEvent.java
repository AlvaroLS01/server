package com.comerzzia.pos.services.payments.events;

import java.util.EventObject;

public class PaymentErrorEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 334201716666453882L;

	private int paymentId;

	private Throwable exception;

	private String erroCode;

	private String errorMessage;

	private boolean removable;

	public PaymentErrorEvent(Object source, int paymentId, Throwable exception, String erroCode, String errorMessage) {
		super(source);
		this.paymentId = paymentId;
		this.exception = exception;
		this.erroCode = erroCode;
		this.errorMessage = errorMessage;
	}

	public String getErroCode() {
		return erroCode;
	}

	public void setErroCode(String erroCode) {
		this.erroCode = erroCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	public boolean isRemovable() {
		return removable;
	}

	public void setRemovable(boolean removable) {
		this.removable = removable;
	}

}
