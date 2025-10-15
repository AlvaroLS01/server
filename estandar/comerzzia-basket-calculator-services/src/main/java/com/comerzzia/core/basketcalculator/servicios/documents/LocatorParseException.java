package com.comerzzia.core.basketcalculator.servicios.documents;

public class LocatorParseException extends Exception {

	private static final long serialVersionUID = 1L;

	public LocatorParseException(String msg) {
		super(msg);
	}
	
	public LocatorParseException(String msg, Throwable e) {
		super(msg, e);
	}
	
}
