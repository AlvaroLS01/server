package com.comerzzia.pos.persistence.tarifas;

import java.io.Serializable;

public class TarifaKey implements Serializable {

	private static final long serialVersionUID = 5168394004236569120L;

	private String uidActividad;

    private String codTarifa;

    public String getUidActividad() {
        return uidActividad;
    }

    public void setUidActividad(String uidActividad) {
        this.uidActividad = uidActividad == null ? null : uidActividad.trim();
    }

    public String getCodTarifa() {
        return codTarifa;
    }

    public void setCodTarifa(String codTarifa) {
        this.codTarifa = codTarifa == null ? null : codTarifa.trim();
    }
}