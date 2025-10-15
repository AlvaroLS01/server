package com.comerzzia.core.basketcalculator.util.mybatis.exception;


public class ConstraintViolationException extends PersistenceException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6143560829444624288L;

	public ConstraintViolationException() {
	}


	
	public ConstraintViolationException(String message, Throwable cause) {
	    super(message, cause);
    }



	public ConstraintViolationException(String message) {
	    super(message);
    }



	public ConstraintViolationException(Throwable cause) {
	    super(cause);
    }



	public String getDefaultMessage() {
		return "Se han violado restricciones de integridad entre registros";
	}
	
	@Override
	public boolean isConstraintViolationException() {
		return true;
	}
	
}
