package com.comerzzia.core.basketcalculator.servicios.mediospago.configuracion;

public class ConfiguracionMedioPagoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6249312380667204506L;

	public ConfiguracionMedioPagoNotFoundException() {
	}

	public ConfiguracionMedioPagoNotFoundException(String msg) {
		super(msg);
	}

	public ConfiguracionMedioPagoNotFoundException(String msg, Throwable e) {
		super(msg, e);
	}
}
