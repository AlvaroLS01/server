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
package com.comerzzia.pos.persistence.tickets;

import java.util.Date;

public class TicketBean extends TicketKey implements Comparable<TicketBean>{
    private String codAlmacen;

    private Long idTicket;

    private Date fecha;

    private Boolean procesado;

    private Date fechaProceso;

    private String mensajeProceso;

    private String codcaja;

    private Long idTipoDocumento;

    private String codTicket;

    private String firma;

    private byte[] ticket;
    
    private String serieTicket;
    
    private String locatorId;
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------
    // implements Comparable<TicketBean> personalizado
    
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------


    public String getCodAlmacen() {
        return codAlmacen;
    }

    public void setCodAlmacen(String codAlmacen) {
        this.codAlmacen = codAlmacen == null ? null : codAlmacen.trim();
    }

    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getProcesado() {
        return procesado;
    }

    public void setProcesado(Boolean procesado) {
        this.procesado = procesado;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public String getMensajeProceso() {
        return mensajeProceso;
    }

    public void setMensajeProceso(String mensajeProceso) {
        this.mensajeProceso = mensajeProceso == null ? null : mensajeProceso.trim();
    }

    public String getCodcaja() {
        return codcaja;
    }

    public void setCodcaja(String codcaja) {
        this.codcaja = codcaja == null ? null : codcaja.trim();
    }

    public Long getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Long idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getCodTicket() {
        return codTicket;
    }

    public void setCodTicket(String codTicket) {
        this.codTicket = codTicket == null ? null : codTicket.trim();
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma == null ? null : firma.trim();
    }

    public byte[] getTicket() {
        return ticket;
    }

    public void setTicket(byte[] ticket) {
        this.ticket = ticket;
    }
    
    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
     public TicketBean() {
    }
    

    @Override
    public int compareTo(TicketBean o) {
        
        return o.getFecha().compareTo(this.getFecha());
    }
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

	public String getSerieTicket() {
		return serieTicket;
	}

	public void setSerieTicket(String serieTicket) {
		this.serieTicket = serieTicket;
	}

	public String getLocatorId() {
		return locatorId;
	}

	public void setLocatorId(String locatorId) {
		this.locatorId = locatorId;
	}

}