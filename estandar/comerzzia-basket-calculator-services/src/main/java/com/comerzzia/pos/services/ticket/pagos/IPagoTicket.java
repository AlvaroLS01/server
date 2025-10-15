package com.comerzzia.pos.services.ticket.pagos;

import java.math.BigDecimal;
import java.util.Map;

import com.comerzzia.pos.persistence.mediosPagos.MedioPagoBean;
import com.comerzzia.pos.services.ticket.pagos.tarjeta.DatosRespuestaPagoTarjeta;

public interface IPagoTicket {

	public abstract String getFormaPago();

	public abstract MedioPagoBean getMedioPago();

	public abstract void setMedioPago(MedioPagoBean medioPago);

	public abstract String getCodMedioPago();

	/**
	 * @param codMedioPago
	 * @deprecated Solo usar en el unmarshall del ticket
	 */
	public abstract void setCodMedioPago(String codMedioPago);

	public abstract String getDesMedioPago();

	/**
	 * @param desMedioPago
	 * @deprecated Solo usar en el unmarshall del ticket
	 */
	public abstract void setDesMedioPago(String desMedioPago);

	public abstract BigDecimal getImporte();

	public abstract void setImporte(BigDecimal importe);

	// Métodos para impresión por pantalla o papel
	public abstract String getImporteAsString();

	public abstract String getCodigoPagoCredito();

	public abstract void setCodigoPagoCredito(String codigoPagoCredito);

	public abstract String getCodigoPagoBanco();

	public abstract void setCodigoPagoBanco(String codigoPagoBanco);

	public abstract boolean isMedioPagoDefecto();

	public abstract boolean isMedioPagoTarjeta();

	public abstract boolean isPagoTarjetaRegalo();

	public abstract boolean isEliminable();

	public abstract void setEliminable(boolean eliminable);

	public abstract DatosRespuestaPagoTarjeta getDatosRespuestaPagoTarjeta();

	public abstract void setDatosRespuestaPagoTarjeta(DatosRespuestaPagoTarjeta datosRespuestaPagoTarjeta);
	
	public Integer getPaymentId();
	
	public void setPaymentId(Integer paymentId);
	
	public Map<String, Object> getExtendedData();
	
	public boolean isIntroducidoPorCajero();
	
	public void setIntroducidoPorCajero(boolean introducidoPorCajero);
	
	boolean isMovimientoCajaInsertado();

}