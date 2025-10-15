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
package com.comerzzia.pos.services.ticket.pagos.tarjeta;

public class DatosDesglose {
    
    protected String nombBanco;
    
    protected String numVentas;
    
    protected String impVentas;
    
    protected String numAbonos;
    
    protected String impAbonos;

    public String getNombBanco() {
        return nombBanco;
    }

    public void setNombBanco(String nombBanco) {
        this.nombBanco = nombBanco;
    }

    public String getNumVentas() {
        return numVentas;
    }

    public void setNumVentas(String numVentas) {
        this.numVentas = numVentas;
    }

    public String getImpVentas() {
        return impVentas;
    }

    public void setImpVentas(String impVentas) {
        this.impVentas = impVentas;
    }

    public String getNumAbonos() {
        return numAbonos;
    }

    public void setNumAbonos(String numAbonos) {
        this.numAbonos = numAbonos;
    }

    public String getImpAbonos() {
        return impAbonos;
    }

    public void setImpAbonos(String impAbonos) {
        this.impAbonos = impAbonos;
    }
}
