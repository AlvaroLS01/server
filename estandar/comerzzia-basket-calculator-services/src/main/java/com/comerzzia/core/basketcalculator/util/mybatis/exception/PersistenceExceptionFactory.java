package com.comerzzia.core.basketcalculator.util.mybatis.exception;

import java.sql.SQLException;

public class PersistenceExceptionFactory {

	public static PersistenceException getPersistenceExpception(org.apache.ibatis.exceptions.PersistenceException e) {
		if (e instanceof PersistenceException){
			return (PersistenceException) e;
		}
		SQLException sqlEx = (SQLException) e.getCause();
		if(sqlEx.getMessage() != null && sqlEx.getMessage().contains("The query has timed out")
				|| sqlEx.getMessage() != null && sqlEx.getMessage().contains("ORA-01013")
				|| sqlEx.getMessage() != null && sqlEx.getMessage().contains("Lock wait timeout")) {
			return new SQLTimeoutException(sqlEx);
		}
		switch (sqlEx.getErrorCode()) {
			// PK y UNQ
			case 1: // ORACLE
			case 1062: // MYSQL
			case 2627: //SQLSERVER
				return new KeyConstraintViolationException(e.getMessage(), e.getCause());
			// NOT NULL
			case 1400: // ORACLE
			case 1048: // MYSQL
				return new NotNullConstraintViolationException(e.getMessage(), e.getCause());
			// FK
			case 2292: // ORACLE
			case 1451: // MYSQL
			case 547: // SQLSERVER
				return new ForeingKeyConstraintViolationException(e.getMessage(), e.getCause());

			default:
				return new ConstraintViolationException(e.getMessage(), e.getCause());
		}

	}
}
