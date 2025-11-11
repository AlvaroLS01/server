package com.comerzzia.api.v2.facturacionmagento.persistence.cajas.lineas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class CajaLineaRecuentoKey {

	private String uidActividad;

	@XmlTransient
	private String uidDiarioCaja;

	@XmlAttribute
	private Integer linea;

	public String getUidActividad() {
		return uidActividad;
	}

	public void setUidActividad(String uidActividad) {
		this.uidActividad = uidActividad == null ? null : uidActividad.trim();
	}

	public String getUidDiarioCaja() {
		return uidDiarioCaja;
	}

	public void setUidDiarioCaja(String uidDiarioCaja) {
		this.uidDiarioCaja = uidDiarioCaja == null ? null : uidDiarioCaja.trim();
	}

	public Integer getLinea() {
		return linea;
	}

	public void setLinea(Integer linea) {
		this.linea = linea;
	}
}
