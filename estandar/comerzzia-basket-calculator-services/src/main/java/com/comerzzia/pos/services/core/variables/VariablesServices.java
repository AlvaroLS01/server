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


package com.comerzzia.pos.services.core.variables;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.pos.persistence.core.variables.POSVariableMapper;
import com.comerzzia.pos.persistence.core.variables.VariableBean;
import com.comerzzia.pos.services.core.sesion.Sesion;

@Service
public class VariablesServices {
    
    protected static final Logger log = Logger.getLogger(VariablesServices.class);
    
    public static final String ARTICULO_DESGLOSE1_TITULO            				= "ARTICULOS.DESGLOSE1_TITULO";
    public static final String ARTICULO_DESGLOSE2_TITULO            				= "ARTICULOS.DESGLOSE2_TITULO";
    public static final String CAJA_REINTENTOS_CIERRE               				= "CAJA.REINTENTOS_CIERRE";
    public static final String CAJA_CIERRE_CAJA_DIARIO_OBLIGATORIO  				= "CAJA.CIERRE_CAJA_DIARIO_OBLIGATORIO";
    public static final String CAJA_APERTURA_AUTOMATICA 							= "CAJA.APERTURA_AUTOMATICA";
    public static final String PROMOCIONES_TRATAR_LINEAS_DEVOLUCION 				= "PROMOCIONES.TRATAR_LINEAS_DEVOLUCION";
    public static final String PROMOCIONES_APLICAR_PROMO_LINEAS_SOBRE_PROMO_PRECIO 	= "PROMOCIONES.APLICACION_PREVIA_DE_PROMOCION_PRECIO";
    public static final String TICKETS_USA_DESCUENTO_EN_LINEA       				= "TICKETS.USA_DESCUENTO_EN_LINEA";
    public static final String TPV_PERMITIR_CAMBIO_DEVOLUCION       				= "TPV.PERMITIR_CAMBIO_DEVOLUCION";
    public static final String TPV_PERMITIR_VENTA_PRECIO_CERO       				= "TPV.PERMITIR_VENTA_PRECIO_CERO";
    public static final String TPV_COLUMNA_VENDEDOR_VISIBLE         				= "TPV.COLUMNA_VENDEDOR_VISIBLE";
    public static final String TPV_VERSION                          				= "TPV.VERSION";
    public static final String WEBSERVICES_APIKEY                   				= "WEBSERVICES.APIKEY";
    public static final String WEBSERVICES_WSCLIENTES               				= "WEBSERVICES.WSCLIENTES";
    public static final String WEBSERVICES_WSUSUARIOS               				= "WEBSERVICES.WSUSUARIOS";
    public static final String TICKETS_PERMITE_CAMBIO_PRECIO        				= "TICKETS.PERMITE_CAMBIO_PRECIO";
    public static final String REST_URL                             				= "REST.URL";   
    public static final String REST_OMNICHANNEL_URL                                 = "REST.OMNICHANNEL.URL";
    public static final String TPV_TRATAR_PROMOCIONES_MENOS_INGRESO 				= "TPV.TRATAR_PROMOCIONES_MENOS_INGRESO";
    public static final String STOCK_TITULO_A 										= "STOCK.TITULO_A";
    public static final String STOCK_TITULO_B 										= "STOCK.TITULO_B";
    public static final String STOCK_TITULO_C										= "STOCK.TITULO_C";
    public static final String STOCK_TITULO_D 										= "STOCK.TITULO_D";
    public static final String CAJA_IMPORTE_MAXIMO_DESCUADRE 						= "CAJA.IMPORTE_MAXIMO_DESCUADRE";
    public static final String CONTADORES_CARACTER_SEPARADOR                        = "CONTADORES.CARACTER_SEPARADOR";
    public static final String IMPORTE_AVISO_RETIRADA                               = "CAJA.IMPORTE_AVISO_RETIRADA";
    public static final String IMPORTE_BLOQUEO_RETIRADA                             = "CAJA.IMPORTE_BLOQUEO_RETIRADA";
    public static final String FIDELIZACION_PUNTOS_FACTOR_CONVERSION 				= "FIDELIZACION.PUNTOS.FACTOR_CONVERSION";
        
    @Autowired
    protected POSVariableMapper variableMapper;
    
    @Autowired
    Sesion sesion;

    /** Carga en sesión todas las variables registradas en el sistema
     * @param uidActividad
     */
    public Map<String, VariableBean> consultarVariables(String uidActividad){
        log.debug("cargarVariables() - Cargando variables para uidActividad: " + uidActividad);
                 
        return variableMapper.selectByActivity(uidActividad);
    }
    
    /**
     * Devuelve el valor de la variable especificada como un BigDecimal
     * @param idVariable
     * @return :: valor
     */
    public BigDecimal getVariableAsBigDecimal(String idVariable) {
        String valor = sesion.getAplicacion().getVariableValue(idVariable);
        if (valor != null){
            return new BigDecimal(valor);
        }
        return BigDecimal.ZERO;
    }
    
    /**
     * Devuelve el valor de la variable especificada como un Long
     * @param idVariable
     * @return
     */
    public Long getVariableAsLong(String idVariable) {
    	String valor = sesion.getAplicacion().getVariableValue(idVariable);
        if (valor != null){
            return new Long(valor);
        }
        return 0L;
    }
    
    /**
     * Devuelve el valor de la variable como un Integer
     * @param idVariable
     * @return
     */
    public Integer getVariableAsInteger(String idVariable) {
    	String valor = sesion.getAplicacion().getVariableValue(idVariable);
        if (valor != null){
            return new Integer(valor);
        }
        return 0;
    }
    
    /**
     * Devuelve el valor de la variable especificada como un Double
     * @param idVariable
     * @return
     */
    public Double getVariableAsDouble(String idVariable) {
    	String valor = sesion.getAplicacion().getVariableValue(idVariable);
        if (valor != null){
            return new Double(valor);
        }
        return 0D;
    }
    
    /**
     * Devuelve el valor de la variable especificada como un Boolean
     * @param idVariable
     * @return
     */
    public Boolean getVariableAsBoolean(String idVariable) {
    	String valor = sesion.getAplicacion().getVariableValue(idVariable);
        return StringUtils.equals(valor, "S");
    }


    /**
     * Devuelve el valor de la variable especificada como un String
     * @param idVariable
     * @return
     */
    public String getVariableAsString(String idVariable) {
    	String valor = sesion.getAplicacion().getVariableValue(idVariable);
    	if (valor != null){
    		return valor;
    	}
    	return "";
    }
    
    public Boolean getVariableAsBoolean(String idVariable, boolean defecto) {
    	String valor = sesion.getAplicacion().getVariableValue(idVariable);
        if (valor != null){
        	return valor.equalsIgnoreCase("S");
        }else{
        	return defecto;
        }
    }

}