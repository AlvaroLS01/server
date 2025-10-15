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
package com.comerzzia.pos.persistence.core.config.configcontadores;

import com.comerzzia.core.basketcalculator.util.base.ParametrosBuscarBean;

public class ParametrosBuscarConfigContadoresBean extends ParametrosBuscarBean {

    /**
     *
     */
    private static final long serialVersionUID = -5461551140871530449L;

    private String idContador;
    private String descripcion;

    public ParametrosBuscarConfigContadoresBean() {
        super.setOrden("ID_CONTADOR");

    }

    public String getIdContador() {
        return idContador;
    }

    public void setIdContador(String idContador) {
        this.idContador = (idContador != null) ? idContador.toUpperCase() : "";
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = (descripcion != null) ? descripcion : "";
    }

}
