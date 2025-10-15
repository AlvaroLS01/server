package com.comerzzia.pos.services.payments;

public class PaymentException extends Exception {
	private static final long serialVersionUID = 1834100841427357331L;
	
	private Object source;
	
	private int paymentId;

	private Throwable exception;

	private String errorCode;

	private boolean removable;
	
	public PaymentException(String message) {
	    super(message);	
	}
	
	public PaymentException(String message, Throwable exception) {
		super(message, exception);
	}
	
	public PaymentException(String message, Throwable exception, int paymentId, Object source) {
		this(message, exception);
		this.paymentId = paymentId;
		this.source = source;
	}
	
	public PaymentException(String message, Throwable exception, int paymentId, Object source, String errorCode) {
		this(message, exception, paymentId, source);
		this.errorCode = errorCode;
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

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isRemovable() {
		return removable;
	}

	public void setRemovable(boolean removable) {
		this.removable = removable;
	}
	
	public Object getSource() {
		return this.source;
	}
	
	public void setSource(Object source) {
		this.source = source;
	}
}
