package com.comerzzia.pos.services.promociones.tipos.especificos.detalles.escalado;

import java.math.BigDecimal;

public class TramoEscalado {

	private BigDecimal cantidadDesde;

	private BigDecimal cantidadHasta;

	private BigDecimal valor;

	public BigDecimal getCantidadDesde() {
		return cantidadDesde;
	}

	public void setCantidadDesde(BigDecimal cantidadDesde) {
		this.cantidadDesde = cantidadDesde;
	}

	public BigDecimal getCantidadHasta() {
		return cantidadHasta;
	}

	public void setCantidadHasta(BigDecimal cantidadHasta) {
		this.cantidadHasta = cantidadHasta;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
