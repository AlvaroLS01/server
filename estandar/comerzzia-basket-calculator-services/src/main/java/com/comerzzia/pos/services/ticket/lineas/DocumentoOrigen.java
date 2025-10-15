package com.comerzzia.pos.services.ticket.lineas;

import java.util.List;

import com.comerzzia.pos.services.ticket.promociones.PromocionLineaTicket;

public class DocumentoOrigen {

	protected String uidDocumentoOrigen;
	protected Integer idLineaDocumentoOrigen;
	protected List<PromocionLineaTicket> promociones;
	
	public DocumentoOrigen() {
	}

	public DocumentoOrigen(String uidDocumentoOrigen, Integer idLineaDocumentoOrigen) {
		this.uidDocumentoOrigen = uidDocumentoOrigen;
		this.idLineaDocumentoOrigen = idLineaDocumentoOrigen;
	}

	public String getUidDocumentoOrigen() {
		return uidDocumentoOrigen;
	}

	public void setUidDocumentoOrigen(String uidDocumentoOrigen) {
		this.uidDocumentoOrigen = uidDocumentoOrigen;
	}

	public Integer getIdLineaDocumentoOrigen() {
		return idLineaDocumentoOrigen;
	}

	public void setIdLineaDocumentoOrigen(Integer idLineaDocumentoOrigen) {
		this.idLineaDocumentoOrigen = idLineaDocumentoOrigen;
	}

	public void setPromociones(List<PromocionLineaTicket> promociones) {
		this.promociones = promociones;
	}

	public List<PromocionLineaTicket> getPromociones() {
		return promociones;
	}
	
}
