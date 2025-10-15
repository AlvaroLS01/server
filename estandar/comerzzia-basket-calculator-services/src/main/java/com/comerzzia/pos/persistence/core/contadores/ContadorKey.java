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

import com.comerzzia.core.basketcalculator.util.base.MantenimientoBean;

public class ContadorKey extends MantenimientoBean {
	
	private static final long serialVersionUID = 7477137153276530918L;

	private String uidActividad;

    private String idContador;

    private String divisor1 = "*";

    private String divisor2 = "*";

    private String divisor3 = "*";

    public String getUidActividad() {
        return uidActividad;
    }

    public void setUidActividad(String uidActividad) {
        this.uidActividad = uidActividad == null ? null : uidActividad.trim();
    }

    public String getIdContador() {
        return idContador;
    }

    public void setIdContador(String idContador) {
        this.idContador = idContador == null ? null : idContador.trim();
    }

    public String getDivisor1() {
        return divisor1;
    }

    public void setDivisor1(String divisor1) {
        this.divisor1 = divisor1 == null ? null : divisor1.trim();
    }

    public String getDivisor2() {
        return divisor2;
    }

    public void setDivisor2(String divisor2) {
        this.divisor2 = divisor2 == null ? null : divisor2.trim();
    }

    public String getDivisor3() {
        return divisor3;
    }

    public void setDivisor3(String divisor3) {
        this.divisor3 = divisor3 == null ? null : divisor3.trim();
    }


    protected void initNuevoBean() {
	    
    }
	
}