package com.comerzzia.pos.persistence.core.config.configcontadores.rangos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.comerzzia.core.basketcalculator.util.base.MantenimientoBean;

@XmlAccessorType(XmlAccessType.NONE)
public class ConfigContadorRangoKey extends MantenimientoBean {

	/**
     * 
     */
    private static final long serialVersionUID = -5930026712668083564L;

    @XmlTransient
	private String idContador;

	@XmlElement(name = "id")
	private String idRango;

	public String getIdContador() {
		return idContador;
	}

	public void setIdContador(String idContador) {
		this.idContador = idContador == null ? null : idContador.trim();
	}

	public String getIdRango() {
		return idRango;
	}

	public void setIdRango(String idRango) {
		this.idRango = idRango == null ? null : idRango.trim();
	}

	@Override
	protected void initNuevoBean() {
		// TODO Auto-generated method stub

	}
}