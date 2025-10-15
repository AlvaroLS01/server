package com.comerzzia.core.basketcalculator.util.mybatis.exception;


public class NotNullConstraintViolationException extends PersistenceException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4062105522859414976L;


	public NotNullConstraintViolationException() {
	    super();
    }


	public NotNullConstraintViolationException(String message, Throwable cause) {
	    super(message, cause);
    }


	public NotNullConstraintViolationException(String message) {
	    super(message);
    }


	public NotNullConstraintViolationException(Throwable cause) {
	    super(cause);
    }


	public String getDefaultMessage() {
		return "Se ha violado una restricción de campo obligatorio";
	}
	
	@Override
	public boolean isNotNullConstraintViolationException() {
		return true;
	}
}
