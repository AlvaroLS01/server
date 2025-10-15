/**
 * ComerZZia 3.0
 *
 * Copyright (c) 2008-2015 Comerzzia, S.L.  All Rights Reserved.
 *
 * THIS WORK IS  SUBJECT  TO  SPAIN  AND  INTERNATIONAL  COPYRIGHT  LAWS  AND
 * TREATIES.   NO  PART  OF  THIS  WORK MAY BE  USED,  PRACTICED,  PERFORMED
 * COPIED, DISTRIBUTED, REVISED, MODIFIED, TRANSLATED,  ABRIDGED, CONDENSED,
 * EXPANDED,  COLLECTED,  COMPILED,  LINKED,  RECAST, TRANSFORMED OR ADAPTED
 * WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION
 * OF THIS WORK WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO
 * CRIMINAL AND CIVIL LIABILITY.
 *
 * CONSULT THE END USER LICENSE AGREEMENT FOR INFORMATION ON ADDITIONAL
 * RESTRICTIONS.
 */
package com.comerzzia.pos.persistence.core.conceptosalmacen;

public class ConceptoAlmacenBean extends ConceptoAlmacenKey {
    private String desconalm;

    private String signo;

    private String codalmOrigen;

    private String codalmDestino;

    private Boolean solicitudAceptacionAuto;

    private Boolean solicitudRecepcionAuto;

    private Boolean solicitudGenerarFaltas;

    private Boolean visibleTiendas;

    private Boolean activo;

    private Short operacionAlmacen;
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
    
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------


    public String getDesconalm() {
        return desconalm;
    }

    public void setDesconalm(String desconalm) {
        this.desconalm = desconalm == null ? null : desconalm.trim();
    }

    public String getSigno() {
        return signo;
    }

    public void setSigno(String signo) {
        this.signo = signo == null ? null : signo.trim();
    }

    public String getCodalmOrigen() {
        return codalmOrigen;
    }

    public void setCodalmOrigen(String codalmOrigen) {
        this.codalmOrigen = codalmOrigen == null ? null : codalmOrigen.trim();
    }

    public String getCodalmDestino() {
        return codalmDestino;
    }

    public void setCodalmDestino(String codalmDestino) {
        this.codalmDestino = codalmDestino == null ? null : codalmDestino.trim();
    }

    public Boolean getSolicitudAceptacionAuto() {
        return solicitudAceptacionAuto;
    }

    public void setSolicitudAceptacionAuto(Boolean solicitudAceptacionAuto) {
        this.solicitudAceptacionAuto = solicitudAceptacionAuto;
    }

    public Boolean getSolicitudRecepcionAuto() {
        return solicitudRecepcionAuto;
    }

    public void setSolicitudRecepcionAuto(Boolean solicitudRecepcionAuto) {
        this.solicitudRecepcionAuto = solicitudRecepcionAuto;
    }

    public Boolean getSolicitudGenerarFaltas() {
        return solicitudGenerarFaltas;
    }

    public void setSolicitudGenerarFaltas(Boolean solicitudGenerarFaltas) {
        this.solicitudGenerarFaltas = solicitudGenerarFaltas;
    }

    public Boolean getVisibleTiendas() {
        return visibleTiendas;
    }

    public void setVisibleTiendas(Boolean visibleTiendas) {
        this.visibleTiendas = visibleTiendas;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Short getOperacionAlmacen() {
        return operacionAlmacen;
    }

    public void setOperacionAlmacen(Short operacionAlmacen) {
        this.operacionAlmacen = operacionAlmacen;
    }
    
    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

}