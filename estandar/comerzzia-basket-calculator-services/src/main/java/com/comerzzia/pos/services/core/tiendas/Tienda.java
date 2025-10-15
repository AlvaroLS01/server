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

package com.comerzzia.pos.services.core.tiendas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.comerzzia.pos.persistence.almacenes.AlmacenBean;
import com.comerzzia.pos.persistence.clientes.ClienteBean;
import com.comerzzia.pos.persistence.core.tiendas.TiendaBean;


@XmlAccessorType(XmlAccessType.NONE)
public class Tienda {

    protected TiendaBean tiendaBean;
    protected AlmacenBean almacenBean;
    protected ClienteBean cliente;
    
    protected Long idTratImpuestos;

    public Tienda() {
        tiendaBean = new TiendaBean();
        almacenBean = new AlmacenBean();
        cliente = new ClienteBean();
    }

    public TiendaBean getTiendaBean() {
        return tiendaBean;
    }

    public void setTiendaBean(TiendaBean tiendaBean) {
        this.tiendaBean = tiendaBean;
    }

    public AlmacenBean getAlmacenBean() {
        return almacenBean;
    }

    public void setAlmacenBean(AlmacenBean empresaBean) {
        this.almacenBean = empresaBean;
    }

    public ClienteBean getCliente() {
        return cliente;
    }

    public void setCliente(ClienteBean cliente) {
        this.cliente = cliente;
        this.idTratImpuestos = cliente.getIdTratImpuestos();
    }

    @XmlElement(name = "codigo")
    public String getCodAlmacen() {
        return almacenBean.getCodAlmacen();
    }

    public void setCodAlmacen(String codAlmacen) {
        this.almacenBean.setCodAlmacen(codAlmacen);
    }

    @XmlElement(name = "descripcion")
    public String getDesAlmacen() {
        return almacenBean.getDesAlmacen();
    }

    public void setDesAlmacen(String desAlmacen) {
        this.almacenBean.setDesAlmacen(desAlmacen);
    }

    @XmlElement
    public String getDomicilio() {
        return almacenBean.getDomicilio();
    }

    public void setDomicilio(String domicilio) {
        this.almacenBean.setDomicilio(domicilio);
    }

    @XmlElement
    public String getPoblacion() {
        return almacenBean.getPoblacion();
    }

    public void setPoblacion(String poblacion) {
        this.almacenBean.setPoblacion(poblacion);
    }

    @XmlElement
    public String getProvincia() {
        return almacenBean.getProvincia();
    }

    public void setProvincia(String provincia) {
        this.almacenBean.setProvincia(provincia);
    }

    @XmlElement
    public String getCp() {
        return almacenBean.getCp();
    }

    public void setCp(String cp) {
        this.almacenBean.setCp(cp);
    }

    @XmlElement
    public String getTelefono1() {
        return almacenBean.getTelefono1();
    }

    public void setTelefono1(String t1) {
        this.almacenBean.setTelefono1(t1);
    }

    @XmlElement
    public String getTelefono2() {
        return almacenBean.getTelefono2();
    }

    public void setTelefono2(String t2) {
        this.almacenBean.setTelefono2(t2);
    }

    @XmlElement
    public String getFax() {
        return almacenBean.getFax();
    }

    public void setFax(String fax) {
        this.almacenBean.setFax(fax);
    }

    @XmlElement(name = "id_trat_impuestos")
    public Long getIdTratamientoImpuestos() {
        return idTratImpuestos;
    }

    public void setIdTratamientoImpuestos(Long idTratImpuestos) {
        this.idTratImpuestos = idTratImpuestos;
    }

}
