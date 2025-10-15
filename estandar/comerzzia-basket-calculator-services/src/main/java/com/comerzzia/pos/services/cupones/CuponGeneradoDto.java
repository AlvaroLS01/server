package com.comerzzia.pos.services.cupones;

import java.math.BigDecimal;

public class CuponGeneradoDto {

	private String codigoCupon;

	private BigDecimal importeCupon;

	public CuponGeneradoDto(String codigoCupon, BigDecimal importeCupon) {
		super();
		this.codigoCupon = codigoCupon;
		this.importeCupon = importeCupon;
	}

	public String getCodigoCupon() {
		return codigoCupon;
	}

	public void setCodigoCupon(String codigoCupon) {
		this.codigoCupon = codigoCupon;
	}

	public BigDecimal getImporteCupon() {
		return importeCupon;
	}

	public void setImporteCupon(BigDecimal importeCupon) {
		this.importeCupon = importeCupon;
	}

}
