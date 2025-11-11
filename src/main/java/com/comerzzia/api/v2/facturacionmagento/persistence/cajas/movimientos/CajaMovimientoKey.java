package com.comerzzia.api.v2.facturacionmagento.persistence.cajas.movimientos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class CajaMovimientoKey {
    private String uidActividad;

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
    
    @Override
	public String toString() {
		return "CajaMovimientoKey [uidActividad=" + uidActividad + ", uidDiarioCaja=" + uidDiarioCaja + ", linea=" + linea + "]";
	}
}
