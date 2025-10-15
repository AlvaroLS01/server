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
package com.comerzzia.pos.persistence.core.tiendas;

import java.util.Date;

public class TiendaBean extends TiendaKey {
    private Long idTipoTienda;

    private Long versionArticulosApl;

    private Long versionArticulosRev;
    
    private Long versionArticulosGen;

    private Boolean activo;

    private Date fechaVersionArticulosApl;

    private Date fechaVersionArticulosRev;
    
    private Date fechaVersionArticulosGen;

    private String codMedioPagoPorDefecto;

    private String codConceptoAlmacenVentas;

    private Long versionCategorizacionesGen;
    
    private Long versionCategorizacionesApl;

    private Long idActVersionArticulosApl;

    private Long idActVersionArticulosRev;
    
    private Long idActVersionArticulosGen;
    
    private String codMedioPagoApartado;

    private String codMedioPagoPromocion;

    private byte[] configuracion;
    
    private String codcanal;
    
    private String codcalserv;
    
    private Double latitud;
    
    private Double longitud;
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
    
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------


    public Long getIdTipoTienda() {
        return idTipoTienda;
    }

    public void setIdTipoTienda(Long idTipoTienda) {
        this.idTipoTienda = idTipoTienda;
    }

    public Long getVersionArticulosApl() {
        return versionArticulosApl;
    }

    public void setVersionArticulosApl(Long versionArticulosApl) {
        this.versionArticulosApl = versionArticulosApl;
    }

    public Long getVersionArticulosRev() {
        return versionArticulosRev;
    }

    public void setVersionArticulosRev(Long versionArticulosRev) {
        this.versionArticulosRev = versionArticulosRev;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Date getFechaVersionArticulosApl() {
        return fechaVersionArticulosApl;
    }

    public void setFechaVersionArticulosApl(Date fechaVersionArticulosApl) {
        this.fechaVersionArticulosApl = fechaVersionArticulosApl;
    }

    public Date getFechaVersionArticulosRev() {
        return fechaVersionArticulosRev;
    }

    public void setFechaVersionArticulosRev(Date fechaVersionArticulosRev) {
        this.fechaVersionArticulosRev = fechaVersionArticulosRev;
    }

    public String getCodMedioPagoPorDefecto() {
        return codMedioPagoPorDefecto;
    }

    public void setCodMedioPagoPorDefecto(String codMedioPagoPorDefecto) {
        this.codMedioPagoPorDefecto = codMedioPagoPorDefecto == null ? null : codMedioPagoPorDefecto.trim();
    }

    public String getCodConceptoAlmacenVentas() {
        return codConceptoAlmacenVentas;
    }

    public void setCodConceptoAlmacenVentas(String codConceptoAlmacenVentas) {
        this.codConceptoAlmacenVentas = codConceptoAlmacenVentas == null ? null : codConceptoAlmacenVentas.trim();
    }

    public Long getVersionCategorizacionesApl() {
        return versionCategorizacionesApl;
    }

    public void setVersionCategorizacionesApl(Long versionCategorizacionesApl) {
        this.versionCategorizacionesApl = versionCategorizacionesApl;
    }

    public Long getIdActVersionArticulosApl() {
        return idActVersionArticulosApl;
    }

    public void setIdActVersionArticulos(Long idActVersionArticulosApl) {
        this.idActVersionArticulosApl = idActVersionArticulosApl;
    }

    public Long getIdActVersionArticulosRev() {
        return idActVersionArticulosRev;
    }

    public void setIdActVersionArticulosRev(Long idActVersionArticulosRev) {
        this.idActVersionArticulosRev = idActVersionArticulosRev;
    }

    public String getCodMedioPagoApartado() {
        return codMedioPagoApartado;
    }

    public void setCodMedioPagoApartado(String codMedioPagoApartado) {
        this.codMedioPagoApartado = codMedioPagoApartado == null ? null : codMedioPagoApartado.trim();
    }

    public String getCodMedioPagoPromocion() {
        return codMedioPagoPromocion;
    }

    public void setCodMedioPagoPromocion(String codMedioPagoPromocion) {
        this.codMedioPagoPromocion = codMedioPagoPromocion == null ? null : codMedioPagoPromocion.trim();
    }

    public byte[] getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(byte[] configuracion) {
        this.configuracion = configuracion;
    }
    
    
    
    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

    public Long getVersionArticulosGen() {
        return versionArticulosGen;
    }

    public void setVersionArticulosGen(Long versionArticulosGen) {
        this.versionArticulosGen = versionArticulosGen;
    }

    public Date getFechaVersionArticulosGen() {
        return fechaVersionArticulosGen;
    }

    public void setFechaVersionArticulosGen(Date fechaVersionArticulosGen) {
        this.fechaVersionArticulosGen = fechaVersionArticulosGen;
    }

    public Long getVersionCategorizacionesGen() {
        return versionCategorizacionesGen;
    }

    public void setVersionCategorizacionesGen(Long versionCategorizacionesGen) {
        this.versionCategorizacionesGen = versionCategorizacionesGen;
    }

    public Long getIdActVersionArticulosGen() {
        return idActVersionArticulosGen;
    }

    public void setIdActVersionArticulosGen(Long idActVersionArticulosGen) {
        this.idActVersionArticulosGen = idActVersionArticulosGen;
    }

	public String getCodcanal() {
		return codcanal;
	}

	public void setCodcanal(String codcanal) {
		this.codcanal = codcanal;
	}

	public String getCodcalserv() {
		return codcalserv;
	}

	public void setCodcalserv(String codcalserv) {
		this.codcalserv = codcalserv;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

}