package com.comerzzia.core.basketcalculator.servicios.pasarelas.configuraciones;

public class ConfiguracionPasarelaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2152462506975436981L;

	public ConfiguracionPasarelaException() {
	}

	public ConfiguracionPasarelaException(String msg) {
		super(msg);
	}

	public ConfiguracionPasarelaException(String msg, Throwable e) {
		super(msg, e);
	}

}