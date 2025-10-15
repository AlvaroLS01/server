package com.comerzzia.pos.services.ticket.cabecera;

import java.math.BigDecimal;

import com.comerzzia.pos.persistence.core.impuestos.porcentajes.PorcentajeImpuestoBean;
import com.comerzzia.pos.services.ticket.lineas.LineaTicketAbstract;

public interface ISubtotalIvaTicket {

	public abstract void addLinea(LineaTicketAbstract linea);

	public abstract void recalcular();

	public abstract String getCodImpuesto();

	/**
	 * @param codImpuesto
	 * @deprecated Solo usar en el unmarshall del ticket
	 */
	public abstract void setCodImpuesto(String codImpuesto);

	public abstract BigDecimal getBase();

	public abstract void setBase(BigDecimal base);

	public abstract BigDecimal getImpuestos();

	public abstract void setImpuestos(BigDecimal impuestos);

	public abstract BigDecimal getTotal();

	public abstract void setTotal(BigDecimal total);

	public abstract BigDecimal getPorcentaje();

	/**
	 * @param porcentajeBD
	 * @deprecated Solo usar en el unmarshall del ticket
	 */
	public abstract void setPorcentaje(BigDecimal porcentajeBD);

	/**
	 * Método para setear el objeto porcentajeBean
	 * @param porcentaje 
	 */
	public abstract void setPorcentajeImpuestoBean(
			PorcentajeImpuestoBean porcentaje);

	public abstract BigDecimal getCuota();

	public abstract void setCuota(BigDecimal cuota);

	public abstract BigDecimal getPorcentajeRecargo();

	/**
	 * @param porcentajeRecargo
	 * @deprecated Solo usar en el unmarshall del ticket
	 */
	public abstract void setPorcentajeRecargo(BigDecimal porcentajeRecargo);

	public abstract BigDecimal getCuotaRecargo();

	public abstract void setCuotaRecargo(BigDecimal cuotaRecargo);

	public abstract String getBaseAsString();

	public abstract String getTotalAsString();

	public abstract String getPorcentajeAsString();

	public abstract String getImpuestosAsString();

	public abstract String getCuotaAsString();

	public abstract String getPorcentajeRecargoAsString();

	public abstract String getCuotaRecargoAsString();

}