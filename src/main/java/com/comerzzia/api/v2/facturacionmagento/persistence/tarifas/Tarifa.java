package com.comerzzia.api.v2.facturacionmagento.persistence.tarifas;

import java.math.BigDecimal;

public class Tarifa {

	private String codTar;
	private String codArt;
	private BigDecimal precioVenta;
	private BigDecimal precioTotal;

	public String getCodTar() {
		return codTar;
	}

	public void setCodTar(String codTar) {
		this.codTar = codTar;
	}

	public String getCodArt() {
		return codArt;
	}

	public void setCodArt(String codArt) {
		this.codArt = codArt;
	}

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}

}
