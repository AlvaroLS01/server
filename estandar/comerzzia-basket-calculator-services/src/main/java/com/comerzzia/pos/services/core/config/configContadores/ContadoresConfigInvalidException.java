package com.comerzzia.pos.services.core.config.configContadores;

import com.comerzzia.pos.util.i18n.I18N;

public class ContadoresConfigInvalidException extends com.comerzzia.pos.util.exception.Exception {

	private static final long serialVersionUID = 1512547824622041705L;

	public ContadoresConfigInvalidException() {
		super();
	}

	public ContadoresConfigInvalidException(String msg) {
		super(msg);
	}

	public ContadoresConfigInvalidException(String msg, Throwable e) {
		super(msg, e);
	}
        
        public ContadoresConfigInvalidException(Throwable cause) {
            super(cause);
        }

        public ContadoresConfigInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }

        @Override
        public String getMessageDefault() {
        	return I18N.getTexto("Error en la longitud del valor del contador");
        }

}
