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
package com.comerzzia.core.basketcalculator.util.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Mantenimiento básico de los datos de un registro
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class MantenimientoBean implements Serializable {
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 5341599003382464139L;

	/**
	 * Definición de constante booleana verdadera como cadena
	 */
	protected static final String TRUE_BOOLEAN = "true";
	/**
	 * Definición de constante booleana falsa como cadena
	 */
	protected static final String FALSE_BOOLEAN = "false";
	
	/**
	 * Definición de constante booleana verdadera como cadena
	 */
	protected static final String TRUE = "S";
	
	/**
	 * Definición de constante booleana falsa como cadena
	 */
	protected static final String FALSE = "N";

	/**
	 * Indica si el registro está activo o no
	 */
	private boolean activo = true;
	
    /**
     * Estado del registro
     */
	@JsonIgnore
	@XmlTransient
    private int estadoBean = Estado.SIN_MODIFICAR;
    
    /**
     * Indica si el registro está en edición o no
     */
	@XmlTransient
    private boolean enEdicion = false;
	
	/**
	 * Mapa de extenesiones para poder personalizar los beans estándar
	 */
	@XmlTransient
	private Map<String, Object> extensiones;
    

    public void setActivo(boolean activo) {
		this.activo = activo;
	}


	public boolean isActivo() {
		return activo;
	}
	
	public void setActivo(String activo) {
		if(activo.equalsIgnoreCase(TRUE_BOOLEAN)){
			activo = TRUE;
		}
		else if(activo.equals(FALSE_BOOLEAN)){
			activo = FALSE;
		}
		this.activo = activo.equals(TRUE);
	}
	
	@JsonIgnore
	public String getActivoString() {
		return (activo) ? TRUE : FALSE;
	}
	
	
    public void setEstadoBean(int estadoBean) {
        this.estadoBean = estadoBean;
        
        if (estadoBean == Estado.NUEVO) {
            initNuevoBean();
        }
    }

	@JsonIgnore
    public int getEstadoBean() {
        return estadoBean;
    }
    
	@JsonIgnore
    public boolean isEstadoBorrado() {
        return estadoBean == Estado.BORRADO;
    }
    
	@JsonIgnore
    public boolean isEstadoNuevo() {
        return estadoBean == Estado.NUEVO;
    }
    
	@JsonIgnore
    public void setEnEdicion(boolean enEdicion) {
        this.enEdicion = enEdicion;
    }

	@JsonIgnore
    public boolean isEnEdicion() {
        return enEdicion;
    }
    
    
    protected abstract void initNuevoBean();

	@JsonIgnore
	public Map<String, Object> getExtensiones() {
		return extensiones;
	}

	public void setExtensiones(Map<String, Object> extensiones) {
		this.extensiones = extensiones;
	}
	
	public void putExtension(String clave, Object valor) {
		if(this.extensiones == null) {
			this.extensiones = new HashMap<String, Object>();
		}
		this.extensiones.put(clave, valor);
	}
	
	@JsonIgnore
	public Object getExtension(String clave) {
		if(this.extensiones == null) {
			this.extensiones = new HashMap<String, Object>();
		}
		return this.extensiones.get(clave);
	}
    
}
