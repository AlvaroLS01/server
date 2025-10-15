package com.comerzzia.core.basketcalculator.servicios.pasarelas.configuraciones;

public class ConfiguracionPasarelaConstraintViolationException extends RuntimeException {

	private static final long serialVersionUID = 5397625194525293958L;

	public ConfiguracionPasarelaConstraintViolationException() {
	}

	public ConfiguracionPasarelaConstraintViolationException(String msg) {
		super(msg);
	}

	public ConfiguracionPasarelaConstraintViolationException(String msg, Throwable e) {
		super(msg, e);
	}
}