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


package com.comerzzia.pos.persistence.fidelizacion;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.format.FormatUtil;
import com.comerzzia.pos.util.xml.MapAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class FidelizacionBean {
    
	@XmlElement(name="idFidelizado")            
    private Long idFidelizado;
	@XmlElement(name="numero_tarjeta")            
    private String numTarjetaFidelizado;
    @XmlElement(name="saldo_puntos")
    private BigDecimal saldoPuntos;
    @XmlElement(name="nombre")
    private String nombre;
    @XmlElement(name="apellidos")
    private String apellido;
	@XmlTransient
    private boolean activa;
	@XmlTransient
    private boolean baja;
	@XmlElement(name="saldo")
    private BigDecimal saldo;
	@XmlElement(name="saldoProvisional")
    private BigDecimal saldoProvisional;
	@XmlTransient
    private List<String> codColectivos;
	@XmlTransient
    private List<String> uidEtiquetas;
	@XmlTransient
    private String domicilio;
	@XmlTransient
    private String poblacion;
	@XmlTransient
    private String provincia;
	@XmlTransient
    private String localidad;
	@XmlTransient
    private String cp;
	@XmlTransient
    private String codTipoIden;
	@XmlTransient
    private String documento;
	@XmlTransient
    private String codPais;
	@XmlTransient
    private String desPais;
	@XmlTransient
	private Boolean paperLess;
	
	@XmlJavaTypeAdapter(MapAdapter.class)
	private Map<String, Object> adicionales;
	
	@XmlTransient
	private List<CustomerCouponDTO> availableCoupons;
	
	@XmlTransient
	private List<CustomerCouponDTO> activeCoupons;
	
    public Long getIdFidelizado() {
		return idFidelizado;
	}

	public void setIdFidelizado(Long idFidelizado) {
		this.idFidelizado = idFidelizado;
	}

	/**
     * @return the numeroTarjeta
     */
    public String getNumTarjetaFidelizado() {
        return numTarjetaFidelizado;
    }

    /**
     * @param numeroTarjeta the numeroTarjeta to set
     */
    public void setNumTarjetaFidelizado(String numTarjetaFidelizado) {
        this.numTarjetaFidelizado = numTarjetaFidelizado;
    }

    /**
     * @return the saldoPuntos
     */
    public BigDecimal getSaldoPuntos() {
        return saldoPuntos;
    }

    /**
     * @param saldoPuntos the saldoPuntos to set
     */
    public void setSaldoPuntos(BigDecimal saldoPuntos) {
        this.saldoPuntos = saldoPuntos;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public boolean isBaja() {
        return baja;
    }

    public void setBaja(boolean baja) {
        this.baja = baja;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public BigDecimal getSaldoTotal() {
    	BigDecimal saldo = getSaldo();
    	BigDecimal saldoProvisional = getSaldoProvisional();
    	if(saldo == null){
    		saldo = BigDecimal.ZERO;
    	}
    	if(saldoProvisional == null){
    		saldoProvisional = BigDecimal.ZERO;
    	}
    	return BigDecimalUtil.redondear(saldo.add(saldoProvisional));
    }
    
    public String getSaldoTotalAsString() {
        return FormatUtil.getInstance().formateaNumero(getSaldoTotal());
    }

    public BigDecimal getSaldoProvisional() {
        return saldoProvisional;
    }

    public void setSaldoProvisional(BigDecimal saldo) {
        this.saldoProvisional = saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

	public List<String> getCodColectivos() {
		return codColectivos;
	}

	public void setCodColectivos(List<String> codColectivos) {
		this.codColectivos = codColectivos;
	}
	
	public List<String> getUidEtiquetas() {
		return uidEtiquetas;
	}

	public void setUidEtiquetas(List<String> uidEtiquetas) {
		this.uidEtiquetas = uidEtiquetas;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getCodTipoIden() {
		return codTipoIden;
	}

	public void setCodTipoIden(String codTipoIden) {
		this.codTipoIden = codTipoIden;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getCodPais() {
		return codPais;
	}

	public void setCodPais(String codPais) {
		this.codPais = codPais;
	}
	
	public String getDesPais() {
		return desPais;
	}

	public void setDesPais(String desPais) {
		this.desPais = desPais;
	}

	public List<CustomerCouponDTO> getAvailableCoupons() {
		return availableCoupons;
	}

	public void setAvailableCoupons(List<CustomerCouponDTO> availableCoupons) {
		this.availableCoupons = availableCoupons;
	}
	
	public List<CustomerCouponDTO> getActiveCoupons() {
		return activeCoupons;
	}

	
	public void setActiveCoupons(List<CustomerCouponDTO> activeCoupons) {
		this.activeCoupons = activeCoupons;
	}

	public Map<String, Object> getAdicionales() {
		return adicionales;
	}

	public void setAdicionales(Map<String, Object> adicionales) {
		this.adicionales = adicionales;
	}
	
	public void putAdicional(String key, Object value) {
		if(this.adicionales == null) {
			this.adicionales = new HashMap<String, Object>();
		}
		
		this.adicionales.put(key, value);
	}

	public Boolean getPaperLess() {
		return paperLess;
	}
	
	public void setPaperLess(Boolean paperLess) {
		this.paperLess = paperLess;
	}
}
