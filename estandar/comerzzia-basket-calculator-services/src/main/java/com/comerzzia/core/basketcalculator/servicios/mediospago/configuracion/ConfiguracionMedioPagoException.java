package com.comerzzia.core.basketcalculator.servicios.mediospago.configuracion;

public class ConfiguracionMedioPagoException extends RuntimeException {

	private static final long serialVersionUID = 3118235099804171938L;

	public ConfiguracionMedioPagoException() {
	}

	public ConfiguracionMedioPagoException(String msg) {
		super(msg);
	}

	public ConfiguracionMedioPagoException(String msg, Throwable e) {
		super(msg, e);
	}

}