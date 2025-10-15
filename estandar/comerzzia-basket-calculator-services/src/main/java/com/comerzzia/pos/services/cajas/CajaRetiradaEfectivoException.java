package com.comerzzia.pos.services.cajas;

import com.comerzzia.pos.util.i18n.I18N;

public class CajaRetiradaEfectivoException extends com.comerzzia.pos.util.exception.Exception{

	private static final long serialVersionUID = 8544955571810957865L;

	public CajaRetiradaEfectivoException() {
    }

    public CajaRetiradaEfectivoException(String message) {
        super(message);
    }

    public CajaRetiradaEfectivoException(String message, Throwable cause) {
        super(message, cause);
    }

    public CajaRetiradaEfectivoException(Throwable cause) {
        super(cause);
    }

    public CajaRetiradaEfectivoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessageDefault() {
    	return I18N.getTexto("Debe realizar una retirada de efectivo para poder continuar");
    }

}
