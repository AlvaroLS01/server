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

package com.comerzzia.pos.util.bigdecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {

    public static final BigDecimal CIEN = new BigDecimal(100);
    
    /** Devuelve el resultado de calcular a la cantidad indicada el tanto por ciento pasado. 
     * Ejemplo: porcentaje(80,20) = 16 // 20% de 80 = 16
     * @param cantidad
     * @param porcentaje
     * @return 
     */
    public static BigDecimal porcentaje(BigDecimal cantidad, BigDecimal porcentaje){
        return cantidad.multiply(porcentaje).divide(CIEN);
    }

    /** Devuelve el resultado de restar a la cantidad indicada el tanto por ciento pasado. 
     * Ejemplo: menosPorcentaje(80,20) = 64 // 80 - 20% = 64
     * @param cantidad
     * @param porcentaje
     * @return 
     */
    public static BigDecimal menosPorcentaje(BigDecimal cantidad, BigDecimal porcentaje){
    	return cantidad.subtract(porcentaje(cantidad, porcentaje));
    }
    
    public static BigDecimal masPorcentaje(BigDecimal cantidad, BigDecimal porcentaje){
    	return cantidad.add(porcentaje(cantidad, porcentaje));
    }
    
    public static BigDecimal menosPorcentajeR(BigDecimal cantidad, BigDecimal porcentaje){
    	return redondear(cantidad.subtract(porcentajeR(cantidad, porcentaje)));
    }
    
    public static BigDecimal masPorcentajeR(BigDecimal cantidad, BigDecimal porcentaje){
    	return redondear(cantidad.add(porcentaje(cantidad, porcentaje)));
    }
    public static BigDecimal menosPorcentajeR4(BigDecimal cantidad, BigDecimal porcentaje){
        return redondear4(cantidad.subtract(porcentajeR4(cantidad, porcentaje)));
    }

    public static BigDecimal masPorcentajeR4(BigDecimal cantidad, BigDecimal porcentaje){
        return redondear4(cantidad.add(porcentajeR4(cantidad, porcentaje)));
    }

    public static BigDecimal porcentajeR(BigDecimal cantidad, BigDecimal porcentaje){
        return redondear(porcentaje(cantidad, porcentaje));
    }
    public static BigDecimal porcentajeR4(BigDecimal cantidad, BigDecimal porcentaje){
        return redondear4(porcentaje(cantidad, porcentaje));
    }
    
    public static BigDecimal redondear(BigDecimal cantidad){
        if (cantidad == null){
            return null;
        }
        
        return redondear(cantidad, 2, BigDecimal.ROUND_HALF_UP);
    }
    public static BigDecimal redondear4(BigDecimal cantidad){
        if (cantidad == null){
            return null;
        }
        return redondear(cantidad, 4, BigDecimal.ROUND_HALF_UP);
    }
    public static BigDecimal redondear(BigDecimal cantidad, int decimales){
        if (cantidad == null){
            return null;        
        }
        return redondear(cantidad, decimales, BigDecimal.ROUND_HALF_UP);
    }
    
    public static BigDecimal redondear(BigDecimal cantidad, int decimales, int roundingMode){
        if (cantidad == null){
            return null;
        }
        return cantidad.setScale(decimales, roundingMode);
    }
    /** Devuelve la cantidad de a la que tras aplicar el porcentaje indicado resulta la cantidad indicada.
     * ejemplo: getAntesDePorcentaje(112, 12) = 100 >> porque 100 + 12% = 112
     * @param resultado
     * @param porcentaje
     * @return  */
    public static BigDecimal getAntesDePorcentaje (BigDecimal resultado, BigDecimal porcentaje){
        return resultado.multiply(CIEN).divide(CIEN.add(porcentaje), 4, RoundingMode.HALF_UP);
    }
    public static BigDecimal getAntesDePorcentaje (BigDecimal resultado, BigDecimal porcentaje, int escala){
        return resultado.multiply(CIEN).divide(CIEN.add(porcentaje), escala, RoundingMode.HALF_UP);
    }
    public static BigDecimal getAntesDePorcentajeR (BigDecimal resultado, BigDecimal porcentaje){
        return redondear(getAntesDePorcentaje(resultado, porcentaje));
    }
            

    /** Devuelve el tanto por ciento de la cantMayor que representa la cantMenor. 
     * Por ejemplo, getTantoPorCientoContenido(50, 20) = 40, Es decir: 40% de 50 = 20
     * @param cantMayor
     * @param cantMenor
     * @return  */
    public static BigDecimal getTantoPorCiento(BigDecimal cantMayor, BigDecimal cantMenor) {
        return (cantMenor.multiply(CIEN)).divide(cantMayor, 4, RoundingMode.HALF_UP);
    }

    /** Devuelve el tanto por ciento restado a una cantidad para poder obtener una segunda cantidad. 
     * Ejemplo: getTantoPorCientoMenos(50, 20) = 60, Es decir: 50 - 60% = 20
     * @param cantidadMayor
     * @param cantidadMenor
     * @return 
     */
    public static BigDecimal getTantoPorCientoMenos(BigDecimal cantidadMayor, BigDecimal cantidadMenor){
    	if(BigDecimalUtil.isIgualACero(cantidadMayor)){
			return BigDecimal.ZERO;
		}
        return (cantidadMayor.subtract(cantidadMenor)).multiply(CIEN).divide(cantidadMayor, 2, RoundingMode.HALF_UP);
    }
    
    public static BigDecimal multiplica(BigDecimal bigDecimal, Integer integer){
        return bigDecimal.multiply(new BigDecimal(integer));
    }
    
    public static boolean isMenor (BigDecimal a, BigDecimal b){
        return a.compareTo(b) < 0;
    }
    public static boolean isMenorACero (BigDecimal a){
        return a.compareTo(BigDecimal.ZERO) < 0;
    }
    public static boolean isMayor (BigDecimal a, BigDecimal b){
        return a.compareTo(b) > 0;
    }
    public static boolean isMayorACero (BigDecimal a){
        return a.compareTo(BigDecimal.ZERO) > 0;
    }
    public static boolean isIgual (BigDecimal a, BigDecimal b){
        return a.compareTo(b) == 0;
    }
    public static boolean isIgualACero (BigDecimal a){
        return a.compareTo(BigDecimal.ZERO) == 0;
    }
    public static boolean isMenorOrIgual (BigDecimal a, BigDecimal b){
        return a.compareTo(b) <= 0;
    }
    public static boolean isMenorOrIgualACero (BigDecimal a){
        return a.compareTo(BigDecimal.ZERO) <= 0;
    }
    public static boolean isMayorOrIgual (BigDecimal a, BigDecimal b){
        return a.compareTo(b) >= 0;
    }
    public static boolean isMayorOrIgualACero (BigDecimal a){
        return a.compareTo(BigDecimal.ZERO) >= 0;
    }
    

}
