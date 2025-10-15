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
package com.comerzzia.pos.persistence.core.conceptosalmacen;

public class ConceptoAlmacenKey {
    private String uidActividad;

    private String codaplicacion;

    private String codconalm;

    public String getUidActividad() {
        return uidActividad;
    }

    public void setUidActividad(String uidActividad) {
        this.uidActividad = uidActividad == null ? null : uidActividad.trim();
    }

    public String getCodaplicacion() {
        return codaplicacion;
    }

    public void setCodaplicacion(String codaplicacion) {
        this.codaplicacion = codaplicacion == null ? null : codaplicacion.trim();
    }

    public String getCodconalm() {
        return codconalm;
    }

    public void setCodconalm(String codconalm) {
        this.codconalm = codconalm == null ? null : codconalm.trim();
    }
}