package com.comerzzia.api.v2.facturacionmagento.persistence.cajas.lineas;

import java.math.BigDecimal;

import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.movimientos.CajaMovimientoBean;
import com.comerzzia.pos.persistence.mediosPagos.MedioPagoBean;
import com.comerzzia.pos.util.format.FormatUtil;

public class CajaLineaAcumuladoBean {

	private MedioPagoBean medioPago;
	private BigDecimal entrada;
	private BigDecimal salida;
	private BigDecimal recuento;

	public CajaLineaAcumuladoBean(MedioPagoBean medioPago) {
		entrada = BigDecimal.ZERO;
		recuento = BigDecimal.ZERO;
		salida = BigDecimal.ZERO;
		this.medioPago = medioPago;
	}

	public void addMovimiento(CajaMovimientoBean movimiento) {
		entrada = entrada.add(movimiento.getCargo());
		salida = salida.add(movimiento.getAbono());
	}

	public void addLineaRecuento(CajaLineaRecuentoBean lineaRecuento) {
		recuento = recuento.add(lineaRecuento.getTotal());
	}

	public MedioPagoBean getMedioPago() {
		return medioPago;
	}

	public BigDecimal getEntrada() {
		return entrada;
	}

	public BigDecimal getSalida() {
		return salida;
	}

	public BigDecimal getRecuento() {
		return recuento;
	}

	public void limpiarRecuento() {
		recuento = BigDecimal.ZERO;
	}

	public BigDecimal getTotal() {
		return getEntrada().subtract(getSalida());
	}

	public BigDecimal getDescuadre() {
		return getRecuento().subtract(getTotal());
	}

	public String getRecuentoAsString() {
		return FormatUtil.getInstance().formateaImporte(getRecuento());
	}

	public String getTotalAsString() {
		return FormatUtil.getInstance().formateaImporte(getTotal());
	}

	public String getDescuadreAsString() {
		return FormatUtil.getInstance().formateaImporte(getDescuadre());
	}

	@Override
	public String toString() {
		return "CajaLineaAcumuladoBean [medioPago=" + medioPago + ", entrada=" + entrada + ", salida=" + salida + ", recuento=" + recuento + "]";
	}

}
