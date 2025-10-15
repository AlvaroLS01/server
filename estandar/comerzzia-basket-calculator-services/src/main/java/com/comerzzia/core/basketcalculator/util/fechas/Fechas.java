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
package com.comerzzia.core.basketcalculator.util.fechas;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Operaciones con fechas
 */
public class Fechas {

    /**
     * Devuelve un sql.Date a partir de un util.Date
     * @param fecha
     * @return 
     */
    public static java.sql.Date toSqlDate(java.util.Date fecha) {
    	if (fecha == null) {
    		return null;
    	}
    	
        return new java.sql.Date(fecha.getTime());
    }
    
    /**
     * Devuelve un sql.Timestamp a partir de un util.Date
     * @param fecha
     * @return 
     */
    public static java.sql.Timestamp toSqlTimestamp(java.util.Date fecha) {
    	if (fecha == null) {
    		return null;
    	}
    	
    	return new Timestamp(fecha.getTime());
    }
    
    /**
     * Suma la cantidad de dias a la fecha pasada
     * 
     * @param fecha a sumar
     * @param cantidad de dias a sumar
     * @return 
     */
    public static Date sumaDias(Date fecha, int cantidad) {
        Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		c.add(Calendar.DAY_OF_MONTH, cantidad);
		fecha = c.getTime();

		return fecha;
    }
    
    /**
     * Suma la cantidad de horas a la fecha pasada
     * 
     * @param fecha a sumar
     * @param cantidad de horas a sumar
     * @return 
     */
    public static Date sumaHoras(Date fecha, int cantidad) {
        Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		c.add(Calendar.HOUR_OF_DAY, cantidad);
		fecha = c.getTime();

		return fecha;
    }
    
    /**
     * Suma la cantidad de minutos a la fecha pasada
     * 
     * @param fecha a sumar
     * @param cantidad de minutos a sumar
     * @return 
     */
    public static Date sumaMinutos(Date fecha, int cantidad) {
        Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		c.add(Calendar.MINUTE, cantidad);
		fecha = c.getTime();

		return fecha;
    }
    
    /**
     * Suma la cantidad de segundos a la fecha pasada
     * 
     * @param fecha a sumar
     * @param cantidad de segundos a sumar
     * @return 
     */
    public static Date sumaSegundos(Date fecha, int cantidad) {
        Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		c.add(Calendar.SECOND, cantidad);
		fecha = c.getTime();

		return fecha;
    }    
    
    
    /**
     * Devuelve la fecha de final del día (23:59:59
     * 
     * @param fecha a convertir
     * @return 
     */
    public static Date setFinalDelDia(Date fecha) {
        Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE,59);
		c.set(Calendar.SECOND,59);
		fecha = c.getTime();

		return fecha;
    }   
    
    /**
     * Devuelve un Date con la fecha pasada
     * @param fecha
     * @return 
     */
    public static Date getFecha(String fecha) throws FechaException {
       return getFecha(fecha, "dd/MM/yyyy");
    }
    
    /**
     * Convierte una fecha en java.util.Date a partir de una cadena y un patrón dado
     * @param fecha
     * @param patron
     * @return 
     */
    public static Date getFecha(String fecha, String patron) throws FechaException {
        Date date = null;
        
        try {
            DateFormat df = new SimpleDateFormat(patron);
            date = df.parse(fecha);
            
            return date;
        }
        catch (ParseException e) {
            throw new FechaException(e.getMessage());
        }
    }

    /**
     * Devuelve el dia de hoy
     * @return 
     */
    public static int dia() {
        Calendar c = Calendar.getInstance();
        
        return c.get(Calendar.DAY_OF_MONTH);
    }
    
    
    /**
     * Devuelve el mes de hoy
     * @return 
     */
    public static int mes() {
        Calendar c = Calendar.getInstance();
        
        return c.get(Calendar.MONTH) + 1;
    }
    
    
    /**
     * Devuelve el año de hoy
     * @return 
     */
    public static int año() {
        Calendar c = Calendar.getInstance();
        
        return c.get(Calendar.YEAR);
    }
    
    /** Devuelve true o false en función de si las fechas indicadas por
     * parámetro son iguales o no (sin tener en cuenta la hora)
     * @param fecha1
     * @param fecha2
     * @return boolean 
     */
    public static boolean equalsDate (Date fecha1, Date fecha2){
	    Calendar c1 = Calendar.getInstance();
	    Calendar c2= Calendar.getInstance();
	    c1.setTime(fecha1);
	    c2.setTime(fecha2);
	    if (c1.get(Calendar.DAY_OF_MONTH) != c2.get(Calendar.DAY_OF_MONTH)){
	    	return false;
	    }
	    if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH)){
	    	return false;
	    }
	    if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR)){
	    	return false;
	    }
	    return true;
	    
    }

    
}
