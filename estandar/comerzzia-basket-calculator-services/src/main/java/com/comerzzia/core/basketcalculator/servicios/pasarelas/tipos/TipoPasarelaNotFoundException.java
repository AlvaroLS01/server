package com.comerzzia.core.basketcalculator.servicios.pasarelas.tipos;

public class TipoPasarelaNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5235047506211511310L;

	public TipoPasarelaNotFoundException() {
	}

	public TipoPasarelaNotFoundException(String msg) {
		super(msg);
	}

	public TipoPasarelaNotFoundException(String msg, Throwable e) {
		super(msg, e);
	}
}
