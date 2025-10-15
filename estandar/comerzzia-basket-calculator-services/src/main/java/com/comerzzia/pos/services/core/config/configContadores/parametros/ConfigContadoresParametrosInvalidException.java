package com.comerzzia.pos.services.core.config.configContadores.parametros;

import com.comerzzia.pos.util.i18n.I18N;

public class ConfigContadoresParametrosInvalidException extends com.comerzzia.pos.util.exception.Exception {

	private static final long serialVersionUID = 1512547824622041705L;

	public ConfigContadoresParametrosInvalidException() {
		super();
	}

	public ConfigContadoresParametrosInvalidException(String msg) {
		super(msg);
	}

	public ConfigContadoresParametrosInvalidException(String msg, Throwable e) {
		super(msg, e);
	}
        
        public ConfigContadoresParametrosInvalidException(Throwable cause) {
            super(cause);
        }

        public ConfigContadoresParametrosInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }

        @Override
        public String getMessageDefault() {
        	return I18N.getTexto("Error en la longitud de un parámetro del contador");
        }

}
