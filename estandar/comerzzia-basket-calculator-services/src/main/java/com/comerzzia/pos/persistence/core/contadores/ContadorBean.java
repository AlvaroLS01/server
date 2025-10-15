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
package com.comerzzia.pos.persistence.core.contadores;

import java.util.Date;

import com.comerzzia.pos.persistence.core.config.configcontadores.ConfigContadorBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.rangos.ConfigContadorRangoBean;

public class ContadorBean extends ContadorKey {
	
	public static final String ERROR_RANGOS = "RANGOS";
	public static final String ERROR_FECHAS = "FECHAS";
	public static final String ERROR_CONTADOR_INVALIDO = "CONTADOR_INVALIDO";
	public static final String ERROR_PARAMETRO_INVALIDO = "PARAMETRO_INVALIDO";
	public static final String ERROR_SALTO_NUMERACION = "SALTO NUMERACION";
	
	private static final long serialVersionUID = -7114486227831000388L;

	private Long valor;

    private Date ultimaPeticion;

    private String idRangoUltimaPeticion;
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
    
    private String valorFormateado;
    
    private ConfigContadorBean configContador;
    
    private ConfigContadorRangoBean configContadorRango;
    
    private String error;
    
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
        
        if(configContador != null){
	        valorFormateado = String.valueOf(valor);
	        
	        if(configContador.getDireccionRelleno() != null && !configContador.getDireccionRelleno().isEmpty()){
	    		if("I".equals(configContador.getDireccionRelleno().toUpperCase())) {
	    			while(valorFormateado.length() < configContador.getLongitudMaxima()) {
	    				valorFormateado = configContador.getCaracterRelleno() + valorFormateado;
	    			}
	    		}
	    		else{
	    			while(valorFormateado.length() < configContador.getLongitudMaxima()) {
	    				valorFormateado = valorFormateado + configContador.getCaracterRelleno();
	    			}
	    		}
	    	}
        }
    }

    public Date getUltimaPeticion() {
        return ultimaPeticion;
    }

    public void setUltimaPeticion(Date ultimaPeticion) {
        this.ultimaPeticion = ultimaPeticion;
    }

    public String getIdRangoUltimaPeticion() {
        return idRangoUltimaPeticion;
    }

    public void setIdRangoUltimaPeticion(String idRangoUltimaPeticion) {
        this.idRangoUltimaPeticion = idRangoUltimaPeticion == null ? null : idRangoUltimaPeticion.trim();
    }
    
    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    
    public ConfigContadorBean getConfigContador() {
    	return configContador;
    }

    public void setConfigContador(ConfigContadorBean configContador) {
    	this.configContador = configContador;
    }

	public String getValorFormateado() {
		return valorFormateado;
	}

	public void setValorFormateado(String valorFormateado) {
		this.valorFormateado = valorFormateado;
	}
	
	public ConfigContadorRangoBean getConfigContadorRango() {
		return configContadorRango;
	}

	public void setConfigContadorRango(ConfigContadorRangoBean configContadorRango) {
		this.configContadorRango = configContadorRango;
	}

	/*
     * Devuelve el valor del contador más próximo en el que se debería enviar una notificación
     */
    public Long getRangoSiguienteAviso(){
    	Long res = null;
    	if(this.getConfigContadorRango()!=null && this.getConfigContadorRango().getRangoAviso() != null){
    		if(this.getConfigContadorRango().getRangoAviso() < this.getValor()){
    			Long hastaProximoAviso = (this.getValor()-this.getConfigContadorRango().getRangoAviso())%this.getConfigContadorRango().getRangoAvisoIntervalo();
        		res = this.getValor()+hastaProximoAviso;
    		}else{
    			res = this.getConfigContadorRango().getRangoAviso();
    		}
    		
    	}
		return res;
	}    
    
    /*
     * Devuelve true si el contador tiene valores disponibles para la fecha actual, false si el contador está completo 
     * o null si el contador no tiene rango cargado
     */
    public Boolean disponible(){
    	Boolean res= null;
    	if(this.getConfigContadorRango()!=null){
    		Date hoy = new Date();
    		res = this.getConfigContadorRango().getRangoFin() > this.getValor()
        			&& this.getConfigContadorRango().getRangoFechaInicio().before(hoy)
        			&& this.getConfigContadorRango().getRangoFechaFin().after(hoy);
    	}
    	return res;
    }

	
    public String getError() {
    	return error;
    }

	
    public void setError(String error) {
    	this.error = error;
    }
    
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

}