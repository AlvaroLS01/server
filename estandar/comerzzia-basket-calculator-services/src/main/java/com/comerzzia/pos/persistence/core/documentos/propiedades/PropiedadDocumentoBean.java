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
package com.comerzzia.pos.persistence.core.documentos.propiedades;

public class PropiedadDocumentoBean extends PropiedadDocumentoKey {
    private String valor;
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
    
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------


    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor == null ? null : valor.trim();
    }
    
    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

}