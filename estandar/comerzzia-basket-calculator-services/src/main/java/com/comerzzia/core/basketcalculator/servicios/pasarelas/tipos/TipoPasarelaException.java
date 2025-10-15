package com.comerzzia.core.basketcalculator.servicios.pasarelas.tipos;

public class TipoPasarelaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2152462506975436981L;

	public TipoPasarelaException() {
	}

	public TipoPasarelaException(String msg) {
		super(msg);
	}

	public TipoPasarelaException(String msg, Throwable e) {
		super(msg, e);
	}

}