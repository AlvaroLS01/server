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
package com.comerzzia.pos.persistence.core.tiendas.cajas;

public class TiendaCajaBean {
    private String uidCaja;

    private String uidActividad;

    private String codAlmacen;

    private String codcaja;

    private String uidTpv;

    private Boolean activo;

    private byte[] configuracion;
    
    private Long idTipoCaja;
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
    
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------


    public String getUidCaja() {
        return uidCaja;
    }

    public void setUidCaja(String uidCaja) {
        this.uidCaja = uidCaja == null ? null : uidCaja.trim();
    }

    public String getUidActividad() {
        return uidActividad;
    }

    public void setUidActividad(String uidActividad) {
        this.uidActividad = uidActividad == null ? null : uidActividad.trim();
    }

    public String getCodAlmacen() {
        return codAlmacen;
    }

    public void setCodAlmacen(String codAlmacen) {
        this.codAlmacen = codAlmacen == null ? null : codAlmacen.trim();
    }

    public String getCodcaja() {
        return codcaja;
    }

    public void setCodcaja(String codcaja) {
        this.codcaja = codcaja == null ? null : codcaja.trim();
    }

    public String getUidTpv() {
        return uidTpv;
    }

    public void setUidTpv(String uidTpv) {
        this.uidTpv = uidTpv == null ? null : uidTpv.trim();
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public byte[] getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(byte[] configuracion) {
        this.configuracion = configuracion;
    }

	public Long getIdTipoCaja() {
		return idTipoCaja;
	}

	public void setIdTipoCaja(Long idTipoCaja) {
		this.idTipoCaja = idTipoCaja;
	}
    
    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

}