package com.comerzzia.pos.services.ticket.cupones;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.comerzzia.pos.services.ticket.lineas.LineaEnTicket;

public class CuponEmitidoTicket {
	protected String tituloCupon;
	protected String descripcionCupon;
	protected String codigoCupon;
	protected Long idPromocionOrigen;
	protected Long idPromocionAplicacion;
	protected BigDecimal importeCupon;
	protected String imagenCupon;
	protected Integer maximoUsos;
	protected Date fechaInicio;
	protected Date fechaFin;
	protected String tipoCupon;

	public CuponEmitidoTicket() {

	}

	public String getTituloCupon() {
		return tituloCupon;
	}

	public void setTituloCupon(String tituloCupon) {
		this.tituloCupon = tituloCupon;
	}

	public String getDescripcionCupon() {
		return descripcionCupon;
	}

	public void setDescripcionCupon(String descripcionCupon) {
		this.descripcionCupon = descripcionCupon;
	}

	public String getCodigoCupon() {
		return codigoCupon;
	}

	public void setCodigoCupon(String codigoCupon) {
		this.codigoCupon = codigoCupon;
	}

	public Long getIdPromocionOrigen() {
		return idPromocionOrigen;
	}

	public void setIdPromocionOrigen(Long idPromocionOrigen) {
		this.idPromocionOrigen = idPromocionOrigen;
	}

	public Long getIdPromocionAplicacion() {
		return idPromocionAplicacion;
	}

	public void setIdPromocionAplicacion(Long idPromocionAplicacion) {
		this.idPromocionAplicacion = idPromocionAplicacion;
	}

	public List<String> getDescripcionFormateada() {
		return new LineaEnTicket(descripcionCupon, true).getLineas();
	}

	public BigDecimal getImporteCupon() {
		return importeCupon;
	}

	public void setImporteCupon(BigDecimal importeCupon) {
		this.importeCupon = importeCupon;
	}

	public String getImagenCupon() {
		return imagenCupon;
	}

	public void setImagenCupon(String imagenCupon) {
		this.imagenCupon = imagenCupon;
	}

	public Integer getMaximoUsos() {
		return maximoUsos;
	}

	public void setMaximoUsos(Integer maximoUsos) {
		this.maximoUsos = maximoUsos;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getTipoCupon() {
		return tipoCupon;
	}

	public void setTipoCupon(String tipoCupon) {
		this.tipoCupon = tipoCupon;
	}

}
