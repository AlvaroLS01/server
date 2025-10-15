package com.comerzzia.core.basketcalculator.servicios.pasarelas.tipos;

public class TipoPasarelaConstraintViolationException extends RuntimeException {

	private static final long serialVersionUID = 5397625194525293958L;

	public TipoPasarelaConstraintViolationException() {
	}

	public TipoPasarelaConstraintViolationException(String msg) {
		super(msg);
	}

	public TipoPasarelaConstraintViolationException(String msg, Throwable e) {
		super(msg, e);
	}
}