package com.comerzzia.pos.services.ticket.lineas;

public class LineaDevolucionNuevoArticuloException extends LineaTicketException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8936239722076729256L;

    public LineaDevolucionNuevoArticuloException() {
    }

    public LineaDevolucionNuevoArticuloException(String message) {
        super(message);
    }

    public LineaDevolucionNuevoArticuloException(String message, Throwable cause) {
        super(message, cause);
    }

    public LineaDevolucionNuevoArticuloException(Throwable cause) {
        super(cause);
    }

    public LineaDevolucionNuevoArticuloException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
