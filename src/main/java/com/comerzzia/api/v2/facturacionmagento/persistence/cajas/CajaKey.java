package com.comerzzia.api.v2.facturacionmagento.persistence.cajas;

public class CajaKey {
    private String uidActividad;

    private String uidDiarioCaja;

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
}