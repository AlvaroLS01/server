package com.comerzzia.api.v2.facturacionmagento.services.facturacion.exception;

public class FacturacionException extends Exception {

	private static final long serialVersionUID = -8356359485296653180L;

	public FacturacionException() {
		super();
	}

	public FacturacionException(String msg) {
		super(msg);
	}

	public FacturacionException(String msg, Throwable e) {
		super(msg, e);
	}

	public FacturacionException(Throwable e) {
		super(e);
	}
}
