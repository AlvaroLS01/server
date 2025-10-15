package com.comerzzia.core.basketcalculator.persistencia.pasarelas.configuraciones;

import com.comerzzia.core.basketcalculator.util.base.ParametrosBuscarBean;

public class ParametrosBuscarConfiguracionPasarelaBean extends ParametrosBuscarBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1949078874145091735L;

	private String idConfPasarela = "";
	private String idTipoPasarela = "";
	private String desConfPasarela = "";
	private String activo = "";

	private static final String TRUE = "S";

	public ParametrosBuscarConfiguracionPasarelaBean() {
		super.setOrden("DESCONFPASARELA");
	}

	public String getIdConfPasarela() {
		return idConfPasarela;
	}

	public void setIdConfPasarela(String idConfPasarela) {
		this.idConfPasarela = idConfPasarela;
	}

	public String getIdTipoPasarela() {
		return idTipoPasarela;
	}

	public void setIdTipoPasarela(String idTipoPasarela) {
		this.idTipoPasarela = idTipoPasarela;
	}

	public String getDesConfPasarela() {
		return desConfPasarela;
	}

	public void setDesConfPasarela(String desConfPasarela) {
		this.desConfPasarela = desConfPasarela;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = (activo != null) ? activo : "";
	}

	public Boolean isActivo() {
		if (activo.equalsIgnoreCase(TRUE)) {
			return true;
		}
		return false;
	}
}