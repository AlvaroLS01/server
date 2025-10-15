package com.comerzzia.pos.services.promociones.tipos.especificos.detalles;

import java.math.BigDecimal;
import java.util.Date;

public class LineaDetallePromocionNxM {

	protected final String codArticulo;
	protected final String desglose1;
	protected final String desglose2;
	protected final BigDecimal descuento;
    protected final Integer cantidadN;
    protected final Integer cantidadM;
    protected final Date fechaInicio;
    protected final Date fechaFin;
    
	public LineaDetallePromocionNxM(String codArticulo, String desglose1, String desglose2, BigDecimal descuento, Integer cantidadN, Integer cantidadM, Date fechaInicio, Date fechaFin) {
		super();
		this.codArticulo = codArticulo;
		this.desglose1 = desglose1;
		this.desglose2 = desglose2;
		this.descuento = descuento;
		this.cantidadN = cantidadN;
		this.cantidadM = cantidadM;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public String getCodArticulo() {
		return codArticulo;
	}

	public String getDesglose1() {
		return desglose1;
	}

	public String getDesglose2() {
		return desglose2;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public Integer getCantidadN() {
		return cantidadN;
	}

	public Integer getCantidadM() {
		return cantidadM;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}
	
}
