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
package com.comerzzia.pos.persistence.clientes;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.comerzzia.core.basketcalculator.util.base.MantenimientoBean;
import com.comerzzia.pos.persistence.tickets.datosfactura.DatosFactura;

@XmlAccessorType(XmlAccessType.NONE)
public class ClienteBean extends ClienteKey implements Cloneable{
    
	@XmlElement(name = "descli")
    private String desCliente;

    @XmlElement(name = "nombre_comercial")
    private String nombreComercial;

    @XmlElement
    private String domicilio;

    @XmlElement
    private String poblacion;

    @XmlElement
    private String provincia;

    @XmlElement
    private String cp;

    @XmlElement
    private String telefono1;

    @XmlElement
    private String telefono2;

    @XmlElement
    private String fax;

    @XmlElement(name = "pais")
    private String codpais;

    @XmlElement(name = "persona_contacto")
    private String personaContacto;

    @XmlElement
    private String email;

    @XmlElement
    private String cif;

    @XmlElement(name = "id_trat_impuestos")
    private Long idTratImpuestos;

    private Long IdMedioPagoVencimiento;

    @XmlElement
    private String codtar;

    @XmlElement
    private String codsec;

    @XmlElement
    private String banco;

    @XmlElement
    private String bancoDomicilio;

    @XmlElement
    private String bancoPoblacion;

    @XmlElement
    private String ccc;

    private String observaciones;

    private Boolean activo;

    private Date fechaAlta;

    private Date fechaBaja;
    
    private String codcliFactura;

    private Double riesgoConcedido;

    @XmlElement
    private String localidad;
    
    private String pais;
    
    @XmlElement (name = "tipo_identificacion")
    private String tipoIdentificacion;
    
    private String deposito;
    
    private String codlengua;
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
    @XmlElement(name = "id_grupo_impuestos")
    private Integer idGrupoImpuestos;
    
    @XmlElement(name = "datos_factura")
    private DatosFactura datosFactura;
    
    @XmlTransient
    private MantenimientoBean mantenimientoBean;
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------


    public ClienteBean() {
    	super();
    	mantenimientoBean = new MantenimientoBean() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void initNuevoBean() {
			}
		};
    }
    
    
    public String getDesCliente() {
        return desCliente;
    }

	public void setDesCliente(String desCliente) {
        this.desCliente = desCliente == null ? null : desCliente.trim();
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

    public String getCodpais() {
        return codpais;
    }

    public void setCodpais(String codpais) {
        this.codpais = codpais == null ? null : codpais.trim();
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto == null ? null : personaContacto.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif == null ? null : cif.trim();
    }

    public Long getIdTratImpuestos() {
        return idTratImpuestos;
    }

    public void setIdTratImpuestos(Long idTratImpuestos) {
        this.idTratImpuestos = idTratImpuestos;
    }

    public Long getIdMedioPagoVencimiento() {
        return IdMedioPagoVencimiento;
    }

    public void setIdMedioPagoVencimiento(Long IdMedioPagoVencimiento) {
        this.IdMedioPagoVencimiento = IdMedioPagoVencimiento;
    }

    public String getCodtar() {
        return codtar;
    }

    public void setCodtar(String codtar) {
        this.codtar = codtar == null ? null : codtar.trim();
    }

    public String getCodsec() {
        return codsec;
    }

    public void setCodsec(String codsec) {
        this.codsec = codsec == null ? null : codsec.trim();
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco == null ? null : banco.trim();
    }

    public String getBancoDomicilio() {
        return bancoDomicilio;
    }

    public void setBancoDomicilio(String bancoDomicilio) {
        this.bancoDomicilio = bancoDomicilio == null ? null : bancoDomicilio.trim();
    }

    public String getBancoPoblacion() {
        return bancoPoblacion;
    }

    public void setBancoPoblacion(String bancoPoblacion) {
        this.bancoPoblacion = bancoPoblacion == null ? null : bancoPoblacion.trim();
    }

    public String getCcc() {
        return ccc;
    }

    public void setCcc(String ccc) {
        this.ccc = ccc == null ? null : ccc.trim();
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones == null ? null : observaciones.trim();
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Double getRiesgoConcedido() {
        return riesgoConcedido;
    }

    public void setRiesgoConcedido(Double riesgoConcedido) {
        this.riesgoConcedido = riesgoConcedido;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }
    
    public String getDeposito() {
		return deposito;
	}


	public void setDeposito(String deposito) {
		this.deposito = deposito;
	}


	public String getCodlengua() {
		return codlengua;
	}


	public void setCodlengua(String codlengua) {
		this.codlengua = codlengua;
	}


	//INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    public Integer getIdGrupoImpuestos() {
        return idGrupoImpuestos;
    }

    public void setIdGrupoImpuestos(Integer idGrupoImpuestos) {
        this.idGrupoImpuestos = idGrupoImpuestos;
    }

    public DatosFactura getDatosFactura() {
        return datosFactura;
    }

    public void setDatosFactura(DatosFactura datosFactura) {
        this.datosFactura = datosFactura;
    }
    
    public boolean isTarifaAsignada(){
        return getCodtar()!=null && !getCodtar().isEmpty();
    }
    
    @Override
    public String toString() {
        return getCodCliente() + " - " + getDesCliente();
    }
    
    public ClienteBean clone(){
        
        Object clienteClone;
        
        try {
            clienteClone = super.clone();
            
            ((ClienteBean)clienteClone).setActivo(this.getActivo());
            ((ClienteBean)clienteClone).setBanco(this.getBanco());
            ((ClienteBean)clienteClone).setBancoDomicilio(this.getBancoDomicilio());
            ((ClienteBean)clienteClone).setBancoPoblacion(this.getBancoPoblacion());
            ((ClienteBean)clienteClone).setCcc(this.getCcc());
            ((ClienteBean)clienteClone).setCif(this.getCif());
            ((ClienteBean)clienteClone).setCodCliente(this.getCodCliente());
            ((ClienteBean)clienteClone).setCodpais(this.getCodpais());
            ((ClienteBean)clienteClone).setCodsec(this.getCodsec());
            ((ClienteBean)clienteClone).setCodtar(this.getCodtar());
            ((ClienteBean)clienteClone).setCp(this.getCp());
            ((ClienteBean)clienteClone).setDatosFactura(this.getDatosFactura());
            ((ClienteBean)clienteClone).setDesCliente(this.getDesCliente());
            ((ClienteBean)clienteClone).setDomicilio(this.getDomicilio());
            ((ClienteBean)clienteClone).setEmail(this.getEmail());
            ((ClienteBean)clienteClone).setFax(this.getFax());
            ((ClienteBean)clienteClone).setFechaAlta(this.getFechaAlta());
            ((ClienteBean)clienteClone).setFechaBaja(this.getFechaBaja());
            ((ClienteBean)clienteClone).setIdGrupoImpuestos(this.getIdGrupoImpuestos());
            ((ClienteBean)clienteClone).setIdMedioPagoVencimiento(this.getIdMedioPagoVencimiento());
            ((ClienteBean)clienteClone).setIdTratImpuestos(this.getIdTratImpuestos());
            ((ClienteBean)clienteClone).setLocalidad(this.getLocalidad());
            ((ClienteBean)clienteClone).setNombreComercial(null);
            ((ClienteBean)clienteClone).setObservaciones(this.getObservaciones());
            ((ClienteBean)clienteClone).setPais(this.getPais());
            ((ClienteBean)clienteClone).setPersonaContacto(this.getPersonaContacto());
            ((ClienteBean)clienteClone).setPoblacion(this.getPoblacion());
            ((ClienteBean)clienteClone).setProvincia(this.getProvincia());
            ((ClienteBean)clienteClone).setRiesgoConcedido(this.getRiesgoConcedido());
            ((ClienteBean)clienteClone).setTelefono1(this.getTelefono1());
            ((ClienteBean)clienteClone).setTelefono2(this.getTelefono2());
            ((ClienteBean)clienteClone).setTipoIdentificacion(this.getTipoIdentificacion());
            ((ClienteBean)clienteClone).setUidActividad(this.getUidActividad());
        }
        catch (CloneNotSupportedException ex) {
            clienteClone = null;
        }
        
        return (ClienteBean)clienteClone;
    }

	public void setEstadoBean(int estadoBean) {
		mantenimientoBean.setEstadoBean(estadoBean);
	}

	public int getEstadoBean() {
		return mantenimientoBean.getEstadoBean();
	}

	public boolean isEstadoBorrado() {
		return mantenimientoBean.isEstadoBorrado();
	}

	public boolean isEstadoNuevo() {
		return mantenimientoBean.isEstadoNuevo();
	}

    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

}