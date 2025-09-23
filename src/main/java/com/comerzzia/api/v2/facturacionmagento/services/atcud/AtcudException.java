package com.comerzzia.api.v2.facturacionmagento.services.atcud;


public class AtcudException extends Exception{


	private static final long serialVersionUID = -1142204154213154807L;
	
	public AtcudException() {
		super();
	}

	public AtcudException(String msg) {
		super(msg);
	}

	public AtcudException(String msg, Throwable e) {
		super(msg, e);
	}

	public AtcudException(Throwable e) {
		super(e);
	}
}
