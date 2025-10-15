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
package com.comerzzia.pos.persistence.core.config.configcontadores;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.comerzzia.pos.persistence.core.config.configcontadores.parametros.ConfigContadorParametroBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.rangos.ConfigContadorRangoBean;


public class ConfigContadorBean {

    private static final long serialVersionUID = 2677375044700851386L;

    private String idContador;

    private String descripcion;

    private String mascaraDivisor1 = "*";

    private String mascaraDivisor2 = "*";

    private String mascaraDivisor3 = "*";

    private Short longitudMaxima;

    private String direccionRelleno;

    private String caracterRelleno;
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
    
    private String valorDivisor1 = "*";
    private String valorDivisor2 = "*";
    private String valorDivisor3 = "*";
    
    private String valorDivisor1Formateado = "*";
    private String valorDivisor2Formateado = "*";
    private String valorDivisor3Formateado = "*";
    
    private boolean parametrosCargados = false;
    
    private boolean rangosCargados = false;
	
    private List<ConfigContadorParametroBean> parametros = new ArrayList<ConfigContadorParametroBean>();
    
    private List<ConfigContadorRangoBean> rangos = new ArrayList<ConfigContadorRangoBean>();
    

    
    private String direccionRellenoStr;
    
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------


    public String getIdContador() {
        return idContador;
    }

    public void setIdContador(String idContador) {
        this.idContador = idContador == null ? null : idContador.trim();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? null : descripcion.trim();
    }

    public String getMascaraDivisor1() {
        return mascaraDivisor1;
    }

    public void setMascaraDivisor1(String mascaraDivisor1) {
        this.mascaraDivisor1 = mascaraDivisor1 == null || mascaraDivisor1.isEmpty() ? "*" : mascaraDivisor1.trim();
    }

    public String getMascaraDivisor2() {
        return mascaraDivisor2;
    }

    public void setMascaraDivisor2(String mascaraDivisor2) {
        this.mascaraDivisor2 = mascaraDivisor2 == null || mascaraDivisor2.isEmpty() ? "*" : mascaraDivisor2.trim();
    }

    public String getMascaraDivisor3() {
        return mascaraDivisor3;
    }

    public void setMascaraDivisor3(String mascaraDivisor3) {
        this.mascaraDivisor3 = mascaraDivisor3 == null || mascaraDivisor3.isEmpty()  ? "*" : mascaraDivisor3.trim();
    }

    public Short getLongitudMaxima() {
        return longitudMaxima;
    }

    public void setLongitudMaxima(Short longitudMaxima) {
        this.longitudMaxima = longitudMaxima;
    }

    public String getDireccionRelleno() {
        return direccionRelleno;
    }

    public void setDireccionRelleno(String direccionRelleno) {
        this.direccionRelleno = direccionRelleno == null ? null : direccionRelleno.trim();
    }

    public String getCaracterRelleno() {
        return caracterRelleno;
    }

    public void setCaracterRelleno(String caracterRelleno) {
        this.caracterRelleno = caracterRelleno == null ? null : caracterRelleno.trim();
    }


    protected void initNuevoBean() {
		parametrosCargados = true;
    }

    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    
	public String getValorDivisor1() {
    	return valorDivisor1;
    }

	
    public void setValorDivisor1(String valorDivisor1) {
    	this.valorDivisor1 = valorDivisor1;
    }

	
    public String getValorDivisor2() {
    	return valorDivisor2;
    }

	
    public void setValorDivisor2(String valorDivisor2) {
    	this.valorDivisor2 = valorDivisor2;
    }

	
    public String getValorDivisor3() {
    	return valorDivisor3;
    }

	
    public void setValorDivisor3(String valorDivisor3) {
    	this.valorDivisor3 = valorDivisor3;
    }
    
    public String getValorDivisor1Formateado() {
    	return valorDivisor1Formateado;
    }

    public void setValorDivisor1Formateado(String valorDivisor1Formateado) {
    	this.valorDivisor1Formateado = valorDivisor1Formateado;
    }

    public String getValorDivisor2Formateado() {
    	return valorDivisor2Formateado;
    }

    public void setValorDivisor2Formateado(String valorDivisor2Formateado) {
    	this.valorDivisor2Formateado = valorDivisor2Formateado;
    }

    public String getValorDivisor3Formateado() {
    	return valorDivisor3Formateado;
    }

    public void setValorDivisor3Formateado(String valorDivisor3Formateado) {
    	this.valorDivisor3Formateado = valorDivisor3Formateado;
    }

	public String formatearValorMascara(String valorMascara) {
    	String valorMascaraFormateada = valorMascara;
    	if(valorMascaraFormateada != null && !"*".equals(valorMascaraFormateada)) {
	    	if(direccionRelleno != null && !direccionRelleno.isEmpty()){
	    		if("I".equals(direccionRelleno.toUpperCase())) {
	    			while(valorMascaraFormateada.length() < longitudMaxima) {
	    				valorMascaraFormateada = caracterRelleno + valorMascaraFormateada;
	    			}
	    		}
	    		else{
	    			while(valorMascaraFormateada.length() < longitudMaxima) {
	    				valorMascaraFormateada = valorMascaraFormateada + caracterRelleno;
	    			}
	    		}
	    	}
    	}
    	return valorMascaraFormateada;
    }

	
    public List<ConfigContadorParametroBean> getParametros() {
    	return parametros;
    }

    public ConfigContadorParametroBean getParametro(String parametro) {
    	ConfigContadorParametroBean res = null;
    	for(ConfigContadorParametroBean param:parametros){
    		if(param.getParametro().equals(parametro)){
    			res = param;
    			break;
    		}
    	}
    	return res;
    }
	
    public void setParametros(List<ConfigContadorParametroBean> parametros) {
    	this.parametros = parametros;
    }

	
    public boolean isParametrosCargados() {
    	return parametrosCargados;
    }

	
    public void setParametrosCargados(boolean parametrosCargados) {
    	this.parametrosCargados = parametrosCargados;
    }

	
    public String getDireccionRellenoStr() {
    	if("I".equals(direccionRelleno)) {
    		direccionRellenoStr = ("Izquierda");
    	}
    	else if("D".equals(direccionRelleno)) {
    		direccionRellenoStr = ("Derecha");
    	}
    	else {
    		direccionRellenoStr = "";
    	}
    	
    	return direccionRellenoStr;
    }

	
    public void setDireccionRellenoStr(String direccionRellenoStr) {
    	this.direccionRellenoStr = direccionRellenoStr;
    }

	public boolean isRangosCargados() {
		return rangosCargados;
	}

	public void setRangosCargados(boolean rangosCargados) {
		this.rangosCargados = rangosCargados;
	}

	public List<ConfigContadorRangoBean> getRangos() {
		return rangos;
	}

	public void setRangos(List<ConfigContadorRangoBean> rangos) {
		this.rangos = rangos;
	}
	
	public ConfigContadorRangoBean getRango(String idRango){
    	ConfigContadorRangoBean res = null;
    	for(ConfigContadorRangoBean rango:this.getRangos()){
    		if(rango.getIdRango().equals(idRango)){
    			res = rango;
    			break;
    		}
    	}
		return res;
    	
    }
	
	public List<ConfigContadorRangoBean> getRangoFechasActivas(){
    	List<ConfigContadorRangoBean> res = new ArrayList<ConfigContadorRangoBean>();
    	Date date = new Date();
    	for(ConfigContadorRangoBean rango:this.getRangos()){
    		if(rango.getRangoFechaInicio().before(date) && rango.getRangoFechaFin().after(date)){
    			res.add(rango);
    		}
    	}
		return res;
    	
    }
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------
}