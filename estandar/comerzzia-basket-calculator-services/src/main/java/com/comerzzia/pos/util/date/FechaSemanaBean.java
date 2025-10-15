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
package com.comerzzia.pos.util.date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.comerzzia.core.basketcalculator.util.fechas.Fecha;

public class FechaSemanaBean {

    private final short LUNES = 0;
    private final short MARTES = 1;
    private final short MIERCOLES = 2;
    private final short JUEVES = 3;
    private final short VIERNES = 4;
    private final short SABADO = 5;
    private final short DOMINGO = 6;
    private final List<Boolean> diasSemana;

    /**Inicializa el objeto con todos los días de la semana activos*/
    public FechaSemanaBean() {
        diasSemana = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            diasSemana.add(true);
        }
    }

    /**Constructor desde String, se le puede pasar cadena en formato "L-.-X-J-.-S-."(13 caracteres) o "L", "M", "X"... por separado(1 carácter).
     * */
    public FechaSemanaBean(String cadenaDias) {
        diasSemana = new ArrayList<>();
        if(cadenaDias.length() == 1){
        	for (int i = 0; i < 6; i++) {
                diasSemana.add(false);
            }
        	
        	switch (cadenaDias) {
        	case "L":
				diasSemana.add(0, true);
				break;
        	case "M":
        		diasSemana.add(1, true);
				break;
        	case "X":
        		diasSemana.add(2, true);
				break;
        	case "J":
        		diasSemana.add(3, true);
				break;
        	case "V":
        		diasSemana.add(4, true);
				break;
        	case "S":
        		diasSemana.add(5, true);
				break;
        	case "D":
        		diasSemana.add(6, true);
				break;

			default:
				throw new IllegalArgumentException("El parámetro tiene un formato incorrecto. Debe ser \"L-.-X-J-.-S-.\"(13 caracteres) o \"L\", \"M\", \"X\"... por separado(1 carácter)");
			}
        }else if(cadenaDias.length() == 13){ //7 días + 6 guiones de separación
        	String[] dias = cadenaDias.split("-");

            for (Integer i = 0; i < dias.length; i++) {
                diasSemana.add(i, !dias[i].equalsIgnoreCase("."));
            }
        }else{
        	throw new IllegalArgumentException("El parámetro tiene un formato incorrecto. Debe ser \"L-.-X-J-.-S-.\"(13 caracteres) o \"L\", \"M\", \"X\"... por separado(1 carácter)");
        }
    }
    
    public Boolean getLunes() {
        return getDiasSemana().get(LUNES);
    }

    public void setLunes(Boolean lunes) {
        getDiasSemana().set(LUNES, lunes);
    }

    public Boolean getMartes() {
        return getDiasSemana().get(MARTES);
    }

    public void setMartes(Boolean martes) {
        getDiasSemana().set(MARTES, martes);
    }

    public Boolean getMiercoles() {
        return getDiasSemana().get(MIERCOLES);
    }

    public void setMiercoles(Boolean miercoles) {
        getDiasSemana().set(MIERCOLES, miercoles);
    }

    public Boolean getJueves() {
        return getDiasSemana().get(JUEVES);
    }

    public void setJueves(Boolean jueves) {
        getDiasSemana().set(JUEVES, jueves);
    }

    public Boolean getViernes() {
        return getDiasSemana().get(VIERNES);
    }

    public void setViernes(Boolean viernes) {
        getDiasSemana().set(VIERNES, viernes);
    }

    public Boolean getSabado() {
        return getDiasSemana().get(SABADO);
    }

    public void setSabado(Boolean sabado) {
        getDiasSemana().set(SABADO, sabado);
    }

    public Boolean getDomingo() {
        return getDiasSemana().get(DOMINGO);
    }

    public void setDomingo(Boolean domingo) {
        getDiasSemana().set(DOMINGO, domingo);
    }

    private List<Boolean> getDiasSemana() {
        return diasSemana;
    }

    public Boolean isFecha(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        Integer diaSemana = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        if (diaSemana < 0) {
            diaSemana = (int) DOMINGO;
        }
        return getDiasSemana().get(diaSemana);
    }

    public Boolean isFecha(Fecha fecha) {
        return isFecha(fecha.getDate());
    }

    @Override
    public String toString() {
        isFecha(new Date());
        String toString = null;
        toString = getLunes() ? "L-" : ".-";
        toString += getMartes() ? "M-" : ".-";
        toString += getMiercoles() ? "X-" : ".-";
        toString += getJueves() ? "J-" : ".-";
        toString += getViernes() ? "V-" : ".-";
        toString += getSabado() ? "S-" : ".-";
        toString += getDomingo() ? "D" : ".";
        return toString;
    }
}
