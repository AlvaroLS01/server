package com.comerzzia.pos.services.ticket.cabecera;

import java.math.BigDecimal;

import com.comerzzia.pos.services.ticket.TicketVenta;
import com.comerzzia.pos.services.ticket.pagos.IPagoTicket;

public interface ITotalesTicket<C extends IPagoTicket> {

	public abstract void recalcular();

	public abstract BigDecimal getPendiente();

	public abstract BigDecimal getTotal();

	public abstract void setTicket(TicketVenta ticket);

	public abstract BigDecimal getEntregado();

	public abstract void setImpuestos(BigDecimal impuestos);

	public abstract void setBase(BigDecimal base);

	public abstract BigDecimal getImpuestos();

	public abstract BigDecimal getBase();
	
	public abstract String getBaseAsString();

	public abstract String getPendienteAsString();

	public abstract String getCambioAsString();

	public abstract String getTotalAsString();

	public abstract String getEntregadoAsString();

	public abstract BigDecimal getTotalSinPromociones();

	public abstract BigDecimal getTotalPromocionesLineas();

	public abstract BigDecimal getTotalPromocionesCabecera();

	public abstract BigDecimal getTotalPromociones();

	public abstract String getTotalPromocionesAsString();

	public abstract C getCambio();

	public abstract void setCambio(C cambio);

	public abstract String getTotalPromocionesCabeceraAsString();

	public abstract BigDecimal getTotalAPagar();

	public abstract void setTotalAPagar(BigDecimal totalAPagar);

	public abstract String getTotalAPagarAsString();

	public abstract boolean hayPromocionesCabecera();

	public abstract int getPuntos();

	public abstract void setPuntos(int puntos);

	public abstract void addPuntos(Integer puntos);

	public abstract void resetPuntos();

	public abstract BigDecimal getEntregadoACuenta();

	public abstract void setEntregadoACuenta(BigDecimal entregadoACuenta);

	public abstract String getEntregadoACuentaAsString();

	public abstract boolean isHayEntregaCuenta();

}