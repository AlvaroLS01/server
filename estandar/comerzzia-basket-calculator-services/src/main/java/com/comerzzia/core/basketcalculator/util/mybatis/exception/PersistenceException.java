package com.comerzzia.core.basketcalculator.util.mybatis.exception;


public class PersistenceException extends org.apache.ibatis.exceptions.PersistenceException {

	private static final long serialVersionUID = -7943231906000585531L;

	
	public PersistenceException() {
	    super();
    }

	public PersistenceException(String message, Throwable cause) {
	    super(message, cause);
    }

	public PersistenceException(String message) {
	    super(message);
    }

	public PersistenceException(Throwable cause) {
	    super(cause);
    }
	
	public boolean isConstraintViolationException(){
		return false;
	}
	public boolean isForeingKeyConstraintViolationException(){
		return false;
	}
	public boolean isKeyConstraintViolationException(){
		return false;
	}
	public boolean isNotNullConstraintViolationException(){
		return false;
	}
	public boolean isSQLTimeoutException(){
		return false;
	}
}
