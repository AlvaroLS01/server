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
package com.comerzzia.pos.persistence.core.impuestos.tratamientos;

public class TratamientoImpuestoBean extends TratamientoImpuestoKey {
    private String codtratimp;

    private String destratimp;

    private String aplicaRecargo;

    private String codpais;

    private String regionImpuestos;
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
    
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------


    public String getCodtratimp() {
        return codtratimp;
    }

    public void setCodtratimp(String codtratimp) {
        this.codtratimp = codtratimp == null ? null : codtratimp.trim();
    }

    public String getDestratimp() {
        return destratimp;
    }

    public void setDestratimp(String destratimp) {
        this.destratimp = destratimp == null ? null : destratimp.trim();
    }

    public String getAplicaRecargo() {
        return aplicaRecargo;
    }

    public void setAplicaRecargo(String aplicaRecargo) {
        this.aplicaRecargo = aplicaRecargo == null ? null : aplicaRecargo.trim();
    }

    public String getCodpais() {
        return codpais;
    }

    public void setCodpais(String codpais) {
        this.codpais = codpais == null ? null : codpais.trim();
    }

    public String getRegionImpuestos() {
        return regionImpuestos;
    }

    public void setRegionImpuestos(String regionImpuestos) {
        this.regionImpuestos = regionImpuestos == null ? null : regionImpuestos.trim();
    }
    
    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    
    public String toString(){
    	return getDestratimp();
    }
    
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

}