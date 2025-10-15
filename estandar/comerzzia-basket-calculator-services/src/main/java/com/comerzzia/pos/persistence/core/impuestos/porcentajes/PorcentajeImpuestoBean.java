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
package com.comerzzia.pos.persistence.core.impuestos.porcentajes;

import java.math.BigDecimal;

public class PorcentajeImpuestoBean extends PorcentajeImpuestoKey {
    private BigDecimal porcentaje;

    private BigDecimal porcentajeRecargo;
    
    private String codimpFiscal; 
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
    
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------


    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public BigDecimal getPorcentajeRecargo() {
        return porcentajeRecargo;
    }

    public void setPorcentajeRecargo(BigDecimal porcentajeRecargo) {
        this.porcentajeRecargo = porcentajeRecargo;
    }
    
    public String getCodimpFiscal() { 
        return codimpFiscal; 
    } 

    public void setCodimpFiscal(String codimpFiscal) { 
    	this.codimpFiscal = codimpFiscal; 
    } 
    
    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    
    public BigDecimal getPorcentajeMasRecargo() {
        return porcentaje.add(porcentajeRecargo);
    }
    
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

}