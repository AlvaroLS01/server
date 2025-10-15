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


package com.comerzzia.pos.services.ticket.promociones;

import java.math.BigDecimal;

import com.comerzzia.pos.services.promociones.DocumentoPromocionable;
import com.comerzzia.pos.services.promociones.filtro.LineasAplicablesPromoBean;
import com.comerzzia.pos.services.promociones.tipos.PromocionLinea;


public class PromocionLineaCandidataTicket {
    protected final LineasAplicablesPromoBean lineasAplicables;
    protected final LineasAplicablesPromoBean lineasCondicion;
    protected final PromocionLinea promocion;

    public PromocionLineaCandidataTicket(LineasAplicablesPromoBean lineasAplicables, LineasAplicablesPromoBean lineasCondicion, PromocionLinea promocion) {
        this.lineasAplicables = lineasAplicables;
        this.lineasCondicion = lineasCondicion;
        this.promocion = promocion;
    }

    public LineasAplicablesPromoBean getLineasAplicables() {
        return lineasAplicables;
    }

    public LineasAplicablesPromoBean getLineasCondicion() {
        return lineasCondicion;
    }

    public PromocionLinea getPromocion() {
        return promocion;
    }
    
    public BigDecimal calcularPromocionCandidata(){
    	return promocion.calcularPromocion(lineasCondicion, lineasAplicables);
    }

    public void aplicarPromocion(DocumentoPromocionable<IPromocionTicket> documento){
    	promocion.aplicarPromocion(documento, lineasCondicion, lineasAplicables);
    }


}
