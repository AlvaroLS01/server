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
package com.comerzzia.pos.persistence.core.empresas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class EmpresaBean extends EmpresaKey {
    @XmlElement(name = "descripcion")
    private String desEmpresa;

    private String domicilio;

    private String poblacion;

    private String provincia;

    private String cp;

    private String telefono1;

    private String telefono2;

    private String fax;

    private String cif;

    @XmlElement(name = "nombre_comercial")
    private String nombreComercial;

    @XmlTransient
    private Boolean activo;

    @XmlTransient
    private String registroMercantil;

    @XmlTransient
    private byte[] logotipo;
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
    
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------


    public String getDesEmpresa() {
        return desEmpresa;
    }

    public void setDesEmpresa(String desEmpresa) {
        this.desEmpresa = desEmpresa == null ? null : desEmpresa.trim();
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial == null ? null : nombreComercial.trim();
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio == null ? null : domicilio.trim();
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion == null ? null : poblacion.trim();
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia == null ? null : provincia.trim();
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp == null ? null : cp.trim();
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif == null ? null : cif.trim();
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1 == null ? null : telefono1.trim();
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2 == null ? null : telefono2.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getRegistroMercantil() {
        return registroMercantil;
    }

    public void setRegistroMercantil(String registroMercantil) {
        this.registroMercantil = registroMercantil == null ? null : registroMercantil.trim();
    }

    public byte[] getLogotipo() {
        return logotipo;
    }

    public void setLogotipo(byte[] logotipo) {
        this.logotipo = logotipo;
    }
    
    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

}