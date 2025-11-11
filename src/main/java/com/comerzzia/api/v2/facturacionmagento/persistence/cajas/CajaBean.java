package com.comerzzia.api.v2.facturacionmagento.persistence.cajas;

import java.util.Date;

public class CajaBean extends CajaKey {

	private String codAlmacen;

	private String codCaja;

	private Date fechaApertura;

	private Date fechaCierre;

	private Date fechaEnvio;

	private String usuario;

	private String usuarioCierre;

	private Date fechaContable;

	public String getCodAlmacen() {
		return codAlmacen;
	}

	public void setCodAlmacen(String codalm) {
		this.codAlmacen = codalm == null ? null : codalm.trim();
	}

	public String getCodCaja() {
		return codCaja;
	}

	public void setCodCaja(String codcaja) {
		this.codCaja = codcaja == null ? null : codcaja.trim();
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario == null ? null : usuario.trim();
	}

	public String getUsuarioCierre() {
		return usuarioCierre;
	}

	public void setUsuarioCierre(String usuarioCierre) {
		this.usuarioCierre = usuarioCierre == null ? null : usuarioCierre.trim();
	}

	public Date getFechaContable() {
		return fechaContable;
	}

	public void setFechaContable(Date fechaContable) {
		this.fechaContable = fechaContable;
	}
}