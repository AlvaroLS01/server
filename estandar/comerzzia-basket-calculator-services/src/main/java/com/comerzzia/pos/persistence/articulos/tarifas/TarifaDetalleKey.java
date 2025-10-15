package com.comerzzia.pos.persistence.articulos.tarifas;

import java.util.Date;

public class TarifaDetalleKey {
    private String uidActividad;

    private String codTarifa;

    private String codArticulo;

    private String desglose1;

    private String desglose2;

    private Date fechaInicio;

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

    public String getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(String codArticulo) {
        this.codArticulo = codArticulo == null ? null : codArticulo.trim();
    }

    public String getDesglose1() {
        return desglose1;
    }

    public void setDesglose1(String desglose1) {
        this.desglose1 = desglose1 == null ? null : desglose1.trim();
    }

    public String getDesglose2() {
        return desglose2;
    }

    public void setDesglose2(String desglose2) {
        this.desglose2 = desglose2 == null ? null : desglose2.trim();
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}