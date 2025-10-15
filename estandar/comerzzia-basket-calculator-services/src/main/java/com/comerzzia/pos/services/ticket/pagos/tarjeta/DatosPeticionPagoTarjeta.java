/**
 * ComerZZia 3.0 Copyright (c) 2008-2015 Comerzzia, S.L. All Rights Reserved. THIS WORK IS SUBJECT TO SPAIN AND
 * INTERNATIONAL COPYRIGHT LAWS AND TREATIES. NO PART OF THIS WORK MAY BE USED, PRACTICED, PERFORMED COPIED,
 * DISTRIBUTED, REVISED, MODIFIED, TRANSLATED, ABRIDGED, CONDENSED, EXPANDED, COLLECTED, COMPILED, LINKED, RECAST,
 * TRANSFORMED OR ADAPTED WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION OF THIS WORK
 * WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO CRIMINAL AND CIVIL LIABILITY. CONSULT THE END USER LICENSE
 * AGREEMENT FOR INFORMATION ON ADDITIONAL RESTRICTIONS.
 */
package com.comerzzia.pos.services.ticket.pagos.tarjeta;

import java.math.BigDecimal;

public class DatosPeticionPagoTarjeta {

	protected BigDecimal importe;

	protected String idTransaccion = "";

	protected String empleado = "";

	protected String codAutorizacion = "";

	protected String numOpBanco = "";

	protected String numOperacion = "";

	protected Long idDocumento;

	protected String idDocumentoOrigen;

	protected String fechaDocumentoOrigen;

	public DatosPeticionPagoTarjeta() {
	}

	public DatosPeticionPagoTarjeta(String idTransaccion, Long idDocumento, BigDecimal importe) {
		this.importe = importe;
		this.idTransaccion = idTransaccion;
		this.idDocumento = idDocumento;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	public String getCodAutorizacion() {
		return codAutorizacion == null ? "" : codAutorizacion;
	}

	public void setCodAutorizacion(String codAutorizacion) {
		this.codAutorizacion = codAutorizacion;
	}

	public String getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(String idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public String getNumOpBanco() {
		return numOpBanco == null ? "" : numOpBanco;
	}

	public void setNumOpBanco(String numOpBanco) {
		this.numOpBanco = numOpBanco;
	}

	public String getNumOperacion() {
		return numOperacion == null ? "" : numOperacion;
	}

	public void setNumOperacion(String numOperacion) {
		this.numOperacion = numOperacion;
	}

	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getIdDocumentoOrigen() {
		return idDocumentoOrigen;
	}

	public void setIdDocumentoOrigen(String idDocumentoOrigen) {
		this.idDocumentoOrigen = idDocumentoOrigen;
	}

	public String getFechaDocumentoOrigen() {
		return fechaDocumentoOrigen;
	}

	public void setFechaDocumentoOrigen(String fechaDocumentoOrigen) {
		this.fechaDocumentoOrigen = fechaDocumentoOrigen;
	}

	@Override
	public String toString() {
		return "DatosPeticionPagoTarjeta [importe=" + importe + ", idTransaccion=" + idTransaccion + ", empleado=" + empleado + ", codAutorizacion=" + codAutorizacion + ", numOpBanco=" + numOpBanco
		        + ", numOperacion=" + numOperacion + "]";
	}

}
