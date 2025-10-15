package com.comerzzia.pos.services.ticket.lineas;

public class LineaDevolucionCambioException extends LineaTicketException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8936239722076729256L;

    public LineaDevolucionCambioException() {
    }

    public LineaDevolucionCambioException(String message) {
        super(message);
    }

    public LineaDevolucionCambioException(String message, Throwable cause) {
        super(message, cause);
    }

    public LineaDevolucionCambioException(Throwable cause) {
        super(cause);
    }

    public LineaDevolucionCambioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
