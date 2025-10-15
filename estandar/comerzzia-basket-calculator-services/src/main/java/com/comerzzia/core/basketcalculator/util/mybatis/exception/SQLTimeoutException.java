package com.comerzzia.core.basketcalculator.util.mybatis.exception;

public class SQLTimeoutException extends PersistenceException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7193157860994198356L;

	
	public SQLTimeoutException() {
	    super();
    }

	public SQLTimeoutException(String message, Throwable cause) {
	    super(message, cause);
    }

	public SQLTimeoutException(String message) {
	    super(message);
    }

	public SQLTimeoutException(Throwable cause) {
	    super(cause);
    }

	public String getDefaultMessage() {
		return "Se ha superado el tiempo de espera para el bloqueo de registros en la base de datos";
	}
	
	@Override
	public boolean isSQLTimeoutException() {
		return true;
	}
}

