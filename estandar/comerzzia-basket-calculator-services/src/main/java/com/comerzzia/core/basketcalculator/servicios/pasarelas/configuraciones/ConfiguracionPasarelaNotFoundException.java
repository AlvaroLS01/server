package com.comerzzia.core.basketcalculator.servicios.pasarelas.configuraciones;

public class ConfiguracionPasarelaNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5235047506211511310L;

	public ConfiguracionPasarelaNotFoundException() {
	}

	public ConfiguracionPasarelaNotFoundException(String msg) {
		super(msg);
	}

	public ConfiguracionPasarelaNotFoundException(String msg, Throwable e) {
		super(msg, e);
	}
}
