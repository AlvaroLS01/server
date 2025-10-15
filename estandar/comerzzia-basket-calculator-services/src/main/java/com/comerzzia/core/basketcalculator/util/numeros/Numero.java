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
package com.comerzzia.core.basketcalculator.util.numeros;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;


public class Numero {

    private Numero() {
    }
    
    /** Compara dos objetos Double para ver si tienen el mismo valor, teniendo en cuenta
     * que pueden ser null cualquiera de ellos. En el caso de que ambos sean null, devuelve
     * true
     * @param d1
     * @param d2
     * @return boolean
     */
    public static boolean equals(Double d1, Double d2){
    	if (d1==null && d2==null){
    		return true;
    	}
    	if ((d1==null && d2!=null) || (d2==null && d1!=null)){
    		return false;
    	}
    	return d1.equals(d2);
    }

    public static Double redondea(double d, int decimales) {
        if (d==0){
        	return 0.0;
        }
    	return new BigDecimal(d).setScale(decimales, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    public static Integer getParteDecimal(Double d, int decimales){
    	return getParteEntera((d - getParteEntera(d)) * Math.pow(10, decimales));
    }

    public static Integer getParteEntera(Double d){
    	return d.intValue();
    }

    @Deprecated
    public static Double desformatea(String numero) {
        return desformatea(numero, 0.0);
    }

    /** Devuelve un Double a partir de una cadena. Si se produce algún error en el parseo, 
     * devolverá el valor por defecto indicado como parámetro.
     * @param numero
     * @param defaultValue
     * @return Double correspondiente a la cadena número
     */
    public static Double desformatea(String numero, Double defaultValue) {
        if (numero == null){
        	return defaultValue;
        }
        DecimalFormat formateador = crearFormateador();
        try { 
        	defaultValue = formateador.parse(numero).doubleValue();
        } 
        catch (ParseException e1) { 
        } 

        return defaultValue;
    }

    
    /** Devuelve un Long a partir de una cadena. Si se produce algún error en el parseo, 
     * devolverá el valor por defecto indicado como parámetro.
     * @param numero
     * @param defaultValue
     * @return Long correspondiente a la cadena número
     */
    public static Long desformateaLong(String numero, Long defaultValue) {
        if (numero == null){
        	return defaultValue;
        }
        DecimalFormat formateador = crearFormateador();
        try { 
        	defaultValue = formateador.parse(numero).longValue();
        } 
        catch (ParseException e1) { 
        } 

        return defaultValue;
    }
    
    /** Devuelve un Integer a partir de una cadena. Si se produce algún error en el parseo, 
     * devolverá el valor por defecto indicado como parámetro.
     * @param numero
     * @param defaultValue
     * @return Integer correspondiente a la cadena número
     */
    public static Integer desformateaInteger(String numero, Integer defaultValue){
        if (numero == null){
        	return defaultValue;
        }
        DecimalFormat formateador = crearFormateador();
        try { 
        	defaultValue = formateador.parse(numero).intValue();
        } 
        catch (ParseException e1) { 
        } 

        return defaultValue;    	
    }

    /** Devuelve un String con un formato determinado a partir de un número.
     * 
     * @param valor
     * @param numDecimales
     * @return String correspondiente al número formateado
     */
    public static String formatea(Object valor, int numDecimales){
		
		DecimalFormat formateador = crearFormateador();
		
		//establecemos las cifras decimales
		formateador.setMinimumFractionDigits(numDecimales);
		formateador.setMaximumFractionDigits(numDecimales);
		
		return formateador.format(valor);
    }
    
    public static String formatea(Object valor){
		
		DecimalFormat formateador = crearFormateador();
		formateador.setMaximumFractionDigits(4);
		
		return formateador.format(valor);
    }

	private static DecimalFormat crearFormateador() {
		//creamos el formateador para nuestra localización
    	DecimalFormat formateador = (DecimalFormat)NumberFormat.getInstance();
		
		return formateador;
	}
	
	/** Parsea una cadena un su valor de número entero. Si el parseo es erróneo devuelve el
	 * valor por defecto indicado.
	 * @param entero
	 * @return Integer 
	 */
	public static Integer getEntero(String entero, Integer valueDefault){
		Integer i = null;
		try{
			i = getEntero(entero);
		}
		catch(ParseException e){
			i = valueDefault;
		}
		return i;
	}
	
	public static Integer getEntero(String entero) throws ParseException{
		Integer i = null;
		try{
			i = Integer.parseInt(entero);
		}
		catch(Exception e){
			throw new ParseException("El valor indicado no es un número entero.", 0);			
		}
		return i;
	}
	
}