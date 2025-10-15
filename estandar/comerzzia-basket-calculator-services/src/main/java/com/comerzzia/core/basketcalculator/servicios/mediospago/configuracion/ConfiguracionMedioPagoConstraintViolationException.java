package com.comerzzia.core.basketcalculator.servicios.mediospago.configuracion;

public class ConfiguracionMedioPagoConstraintViolationException extends RuntimeException {

	private static final long serialVersionUID = 3512474571439420169L;

	public ConfiguracionMedioPagoConstraintViolationException() {
	}

	public ConfiguracionMedioPagoConstraintViolationException(String msg) {
		super(msg);
	}

	public ConfiguracionMedioPagoConstraintViolationException(String msg, Throwable e) {
		super(msg, e);
	}

}