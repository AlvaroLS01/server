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


package com.comerzzia.pos.services.ticket.cabecera;

public class DatosDocumentoOrigenTicket {
    protected String serie;
    protected String caja;
    protected Long numFactura;
    protected Long idTipoDoc;
    protected String codTipoDoc;
    protected String desTipoDoc;
    protected String uidTicket;
    protected String codTicket;
    protected String fecha;
    protected String tienda;
    protected boolean recoveredOnline;
    
    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public Long getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(Long numFactura) {
        this.numFactura = numFactura;
    }

    public Long getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(Long idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    public String getCodTipoDoc() {
        return codTipoDoc;
    }

    public void setCodTipoDoc(String codTipoDoc) {
        this.codTipoDoc = codTipoDoc;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

	public String getDesTipoDoc() {
		return desTipoDoc;
	}

	public void setDesTipoDoc(String desTipoDoc) {
		this.desTipoDoc = desTipoDoc;
	}

	public String getCodTicket() {
		return codTicket;
	}

	public void setCodTicket(String codTicket) {
		this.codTicket = codTicket;
	}

	public String getTienda() {
		return tienda;
	}

	public void setTienda(String tienda) {
		this.tienda = tienda;
	}

	public String getUidTicket() {
		return uidTicket;
	}

	public void setUidTicket(String uidTicket) {
		this.uidTicket = uidTicket;
	}

	public boolean isRecoveredOnline() {
		return recoveredOnline;
	}

	public void setRecoveredOnline(boolean recoveredOnline) {
		this.recoveredOnline = recoveredOnline;
	}
    
}