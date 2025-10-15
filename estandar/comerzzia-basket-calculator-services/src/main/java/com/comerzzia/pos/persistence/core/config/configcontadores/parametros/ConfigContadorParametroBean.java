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
package com.comerzzia.pos.persistence.core.config.configcontadores.parametros;



public class ConfigContadorParametroBean extends ConfigContadorParametroKey {

    private static final long serialVersionUID = 1028894098404958509L;

	public static final String PARAMETRO_CODEMP = "CODEMP";
	public static final String PARAMETRO_CODALM = "CODALM";
	public static final String PARAMETRO_CODSERIE = "CODSERIE";
	public static final String PARAMETRO_CODCAJA = "CODCAJA";
	public static final String PARAMETRO_PERIODO = "PERIODO";
	public static final String PARAMETRO_CODDOC = "CODTIPODOCUMENTO";
	public static final String PARAMETRO_RANGO = "id_rango";

	private Short longitudMaxima;

    private String direccionRelleno;

    private String caracterRelleno;
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
    
    private String valorParametro;
    
    private String direccionRellenoStr;
    
    private String valorParametroFormateado;
    
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------
    
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

    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    
    public String getValorParametro() {
    	return valorParametro;
    }

	
    public void setValorParametro(String valorParametro) {
    	this.valorParametro = valorParametro;
    	valorParametroFormateado = valorParametro;
    	if(valorParametroFormateado != null) {
	    	if(direccionRelleno != null && !direccionRelleno.isEmpty()){
	    		if("I".equals(direccionRelleno.toUpperCase())) {
	    			while(valorParametroFormateado.length() < longitudMaxima) {
	    				valorParametroFormateado = caracterRelleno + valorParametroFormateado;
	    			}
	    		}
	    		else{
	    			while(valorParametroFormateado.length() < longitudMaxima) {
	    				valorParametroFormateado = valorParametroFormateado + caracterRelleno;
	    			}
	    		}
	    	}
    	}
    }
    
    public String getValorParametroFormateado() {
    	return valorParametroFormateado;
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
    
    
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

}