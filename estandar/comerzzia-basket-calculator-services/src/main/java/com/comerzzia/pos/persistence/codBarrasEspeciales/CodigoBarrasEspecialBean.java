/**
 * ComerZZia 3.0 Copyright (c) 2008-2015 Comerzzia, S.L. All Rights Reserved. THIS WORK IS SUBJECT TO SPAIN AND
 * INTERNATIONAL COPYRIGHT LAWS AND TREATIES. NO PART OF THIS WORK MAY BE USED, PRACTICED, PERFORMED COPIED,
 * DISTRIBUTED, REVISED, MODIFIED, TRANSLATED, ABRIDGED, CONDENSED, EXPANDED, COLLECTED, COMPILED, LINKED, RECAST,
 * TRANSFORMED OR ADAPTED WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION OF THIS WORK
 * WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO CRIMINAL AND CIVIL LIABILITY. CONSULT THE END USER LICENSE
 * AGREEMENT FOR INFORMATION ON ADDITIONAL RESTRICTIONS.
 */
package com.comerzzia.pos.persistence.codBarrasEspeciales;

public class CodigoBarrasEspecialBean extends CodigoBarrasEspecialKey {

	private String descripcion;

	private String prefijo;

	private String codart;

	private String precio;

	private String cantidad;

	private String fidelizacion;

	private String codticket;

	// INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------

	private String codigoIntroducido;

	// FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion == null ? null : descripcion.trim();
	}

	public String getPrefijo() {
		return prefijo;
	}

	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo == null ? null : prefijo.trim();
	}

	public String getCodart() {
		return codart;
	}

	public void setCodart(String codart) {
		this.codart = codart == null ? null : codart.trim();
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio == null ? null : precio.trim();
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad == null ? null : cantidad.trim();
	}

	public String getFidelizacion() {
		return fidelizacion;
	}

	public void setFidelizacion(String fidelizacion) {
		this.fidelizacion = fidelizacion == null ? null : fidelizacion.trim();
	}

	public String getCodticket() {
		return codticket;
	}

	public void setCodticket(String codticket) {
		this.codticket = codticket == null ? null : codticket.trim();
	}

	// INICIO MÉTODOS PERSONALIZADOS--------------------------------------------

	public String getCodigoIntroducido() {
		return codigoIntroducido;
	}

	public void setCodigoIntroducido(String codigoIntroducido) {
		this.codigoIntroducido = codigoIntroducido;
	}

	// FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

}