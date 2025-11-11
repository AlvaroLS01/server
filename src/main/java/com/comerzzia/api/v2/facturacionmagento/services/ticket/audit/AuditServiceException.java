package com.comerzzia.api.v2.facturacionmagento.services.ticket.audit;

public class AuditServiceException extends Exception{

	private static final long serialVersionUID = -9022338640196786603L;

	public AuditServiceException() {
    }

    public AuditServiceException(String message) {
        super(message);
    }

    public AuditServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuditServiceException(Throwable cause) {
        super(cause);
    }

    public AuditServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
