package com.comerzzia.api.v2.facturacionmagento.persistence.motivos;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "codigo_tipo", "descripcion", "comentario", "precioSinDtoOriginal", "precioSinDtoAplicado" })
public class Motivo extends MotivoKey {

	@XmlElement(name = "codigo_tipo")
	private String codigo_tipo;

	@XmlElement(name = "descripcion")
	private String descripcion;

	@XmlElement(name = "comentario")
	private String comentario;
	
	@XmlElement(name = "precio_articulo_original")
	protected BigDecimal precioSinDtoOriginal;
	
	@XmlElement(name = "precio_articulo_aplicado")
	protected BigDecimal precioSinDtoAplicado;

	public String getCodigoTipo() {
		return codigo_tipo;
	}

	public void setCodigoTipo(String codigoTipo) {
		this.codigo_tipo = codigoTipo == null ? null : codigoTipo.trim();
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion == null ? null : descripcion.trim();
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario == null ? null : comentario.trim();
	}

	public BigDecimal getPrecioSinDtoOriginal() {
		return precioSinDtoOriginal;
	}

	public void setPrecioSinDtoOriginal(BigDecimal precioSinDtoOriginal) {
		this.precioSinDtoOriginal = precioSinDtoOriginal;
	}

	public BigDecimal getPrecioSinDtoAplicado() {
		return precioSinDtoAplicado;
	}

	public void setPrecioSinDtoAplicado(BigDecimal precioSinDtoAplicado) {
		this.precioSinDtoAplicado = precioSinDtoAplicado;
	}
	
	
}