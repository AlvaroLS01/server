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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatosRespuestaPagoTarjeta {
    
	protected DatosPeticionPagoTarjeta datosPeticion; 
	
    protected String codResult;
    protected String msgRespuesta;
    protected String ticket; // Nº operacion del terminal
    protected String tipoOp;
    protected String importe;
    protected String AID; // Identificador de la aplicación CHIP // OPCIONAL
    protected String tarjeta;
    protected String marcaTarjeta;
    protected String descripcionMarcaTarjeta; // OPCIONAL
    protected String titular; // OPCIONAL
    protected String comercio;
    protected String terminal; //Identificación del terminal.
    protected String descBanco; // OPCIONAL
    protected String ARC; // OPCIONAL
    protected String numOperacionBanco;
    protected String codAutorizacion;
    protected String moneda;
    protected String numOperacion;
    protected String tipoLectura; // OPCIONAL //0 Banda, 2 Chip EMV. resultado de la función leerTarjeta
    protected String verificacion; // OPCIONAL //modo de verificacion del usuario (PIN,Firma,...)
    protected String numTransaccion; // OPCIONAL
    
    protected String pos;
    protected String codigoCentro;
    protected String codigoTienda;
    protected String codigoCajera; //Cajero
    protected String tipoTransaccion; //Tipo de Operación.
    protected String fuc; //Código F.U.C. asignado al establecimiento.
    protected String nombreEntidad; //identificador HCP (sistema4b,servired,...)
    protected String PAN; //PAN, número de tarjeta.
    protected String P23; //num secuencial de la tarjeta
    protected String applicationLabel; //Etiqueta del terminal solo EMV
    protected String fechaTransaccion; //Fecha y hora local de la transacción. Formato DDMMAA hhmm.
    protected String establecimiento;
    protected String direccionEstablecimiento;
    //protected String requestCode = ARC
    protected String terminalId; //numero del pinpad
    //protected String verificacionTitular = verificacion
    protected String authMode; //Identificación del centro autorizador.
    protected String contactLess; //0 No contactless, 1 Contactless
    protected String nombredf;
	    

    protected boolean pedirFirma;

    protected List<DatosDesglose> desgloses; // OPCIONAL
    
    protected Map<String, String> adicionales;

    //Datos pago DCC // OPCIONAL
    protected String importeDivisa;
    protected String codigoDivisa;
    protected String exchangeRate;
    protected String comision;
    protected boolean DCC;
    
    /* Datos necesarios para imprimir boleta */
    protected String empleado;
    protected String ATC;
    protected String documento;
    protected String sesionId;
    protected String fechaBoleta;
    protected String horaBoleta;
    
    public DatosRespuestaPagoTarjeta(){
    }
    
    public DatosRespuestaPagoTarjeta(DatosPeticionPagoTarjeta peticion){
    	this.datosPeticion = peticion;
    }

    public void addAdicional(String key, String value){
    	if (adicionales == null){
    		adicionales = new HashMap<String, String>();
    	}
    	adicionales.put(key, value);
    }
    
    public String getAdicional(String key){
    	if (adicionales == null){
    		return null;
    	}
    	return adicionales.get(key);
    }
    
    public Map<String, String> getAdicionales(){
    	return adicionales;
    }
    public void setAdicionales(Map<String, String> adicionales){
    	this.adicionales = adicionales;
    }
    
    public String getCodResult() {
        return codResult;
    }

    public void setCodResult(String codResult) {
        this.codResult = codResult;
    }

    public String getMsgRespuesta() {
        return msgRespuesta;
    }

    public void setMsgRespuesta(String msgRespuesta) {
        this.msgRespuesta = msgRespuesta;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getTipoOp() {
        return tipoOp;
    }

    public void setTipoOp(String tipoOp) {
        this.tipoOp = tipoOp;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getAID() {
        return AID;
    }

    public boolean tieneAID() {
        return AID != null;
    }

    public void setAID(String AID) {
        this.AID = AID;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getDescripcionMarcaTarjeta() {
        return descripcionMarcaTarjeta;
    }

    public void setDescripcionMarcaTarjeta(String descripcionMarcaTarjeta) {
        this.descripcionMarcaTarjeta = descripcionMarcaTarjeta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }
    
    public boolean tieneTitular(){
    	return titular != null;
    }

    public String getComercio() {
        return comercio;
    }

    public void setComercio(String comercio) {
        this.comercio = comercio;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getDescBanco() {
        return descBanco;
    }

    public void setDescBanco(String descBanco) {
        this.descBanco = descBanco;
    }

    public String getARC() {
        return ARC;
    }

    public void setARC(String ARC) {
        this.ARC = ARC;
    }

    public String getNumOperacionBanco() {
        return numOperacionBanco;
    }

    public void setNumOperacionBanco(String numOpBanco) {
        this.numOperacionBanco = numOpBanco;
    }

    public String getCodAutorizacion() {
        return codAutorizacion;
    }

    public void setCodAutorizacion(String codAutorizacion) {
        this.codAutorizacion = codAutorizacion;
    }

    public boolean getPedirFirma() {
        return pedirFirma;
    }

    public void setPedirFirma(boolean pedirFirma) {
        this.pedirFirma = pedirFirma;
    }

    public String getNumOperacion() {
        return numOperacion;
    }

    public void setNumOperacion(String numOperacion) {
        this.numOperacion = numOperacion;
    }

    public List<DatosDesglose> getDesgloses() {
        return desgloses;
    }

    public void setDesgloses(List<DatosDesglose> desgloses) {
        this.desgloses = desgloses;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getComision() {
        return comision;
    }

    public void setComision(String comision) {
        this.comision = comision;
    }

    public boolean isDCC() {
        return DCC;
    }

    public void setDCC(boolean DCC) {
        this.DCC = DCC;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
    
    public String getImporteDivisa() {
    	return importeDivisa;
    }

	
    public void setImporteDivisa(String importeDivisa) {
    	this.importeDivisa = importeDivisa;
    }

	
    public String getCodigoDivisa() {
    	return codigoDivisa;
    }

	
    public void setCodigoDivisa(String codigoDivisa) {
    	this.codigoDivisa = codigoDivisa;
    }

	public String getMarcaTarjeta() {
    	return marcaTarjeta;
    }

	
    public void setMarcaTarjeta(String marcaTarjeta) {
    	this.marcaTarjeta = marcaTarjeta;
    }

	public DatosPeticionPagoTarjeta getDatosPeticion() {
		return datosPeticion;
	}

	public void setDatosPeticion(DatosPeticionPagoTarjeta datosPeticion) {
		this.datosPeticion = datosPeticion;
	}

    public String getVerificacion() {
    	return verificacion;
    }

    public boolean tieneVerificacion() {
    	return verificacion != null;
    }

	
    public void setVerificacion(String verificacion) {
    	this.verificacion = verificacion;
    }

	
    public String getNumTransaccion() {
    	return numTransaccion;
    }

    public boolean tieneNumTransaccion() {
    	return numTransaccion != null;
    }

	
    public void setNumTransaccion(String numTransaccion) {
    	this.numTransaccion = numTransaccion;
    }

	public String getTipoLectura() {
    	return tipoLectura;
    }

	
    public void setTipoLectura(String tipoLectura) {
    	this.tipoLectura = tipoLectura;
    }
    
	public String getFuc() {
		return fuc;
	}

	public void setFuc(String fuc) {
		this.fuc = fuc;
	}

	public String getNombreEntidad() {
		return nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	public String getPAN() {
		return PAN;
	}

	public void setPAN(String pAN) {
		PAN = pAN;
	}

	public String getP23() {
		return P23;
	}

	public void setP23(String p23) {
		P23 = p23;
	}

	public String getApplicationLabel() {
		return applicationLabel;
	}

	public void setApplicationLabel(String applicationLabel) {
		this.applicationLabel = applicationLabel;
	}

	public String getFechaTransaccion() {
		return fechaTransaccion;
	}

	public void setFechaTransaccion(String fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getAuthMode() {
		return authMode;
	}

	public void setAuthMode(String authMode) {
		this.authMode = authMode;
	}

	public String getContactLess() {
		return contactLess;
	}

	public void setContactLess(String contactLess) {
		this.contactLess = contactLess;
	}
	
	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getCodigoCentro() {
		return codigoCentro;
	}

	public void setCodigoCentro(String codigoCentro) {
		this.codigoCentro = codigoCentro;
	}

	public String getCodigoTienda() {
		return codigoTienda;
	}

	public void setCodigoTienda(String codigoTienda) {
		this.codigoTienda = codigoTienda;
	}

	public String getCodigoCajera() {
		return codigoCajera;
	}

	public void setCodigoCajera(String codigoCajera) {
		this.codigoCajera = codigoCajera;
	}

	public String getTipoTransaccion() {
		return tipoTransaccion;
	}

	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getDireccionEstablecimiento() {
		return direccionEstablecimiento;
	}

	public void setDireccionEstablecimiento(String direccionEstablecimiento) {
		this.direccionEstablecimiento = direccionEstablecimiento;
	}
	
	public String getNombredf() {
		return nombredf;
	}

	public void setNombredf(String nombredf) {
		this.nombredf = nombredf;
	}
	
	public String getATC() {
		return ATC;
	}

	public void setATC(String aTC) {
		ATC = aTC;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getSesionId() {
		return sesionId;
	}

	public void setSesionId(String sesionId) {
		this.sesionId = sesionId;
	}
	
	public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}
	
	public String getFechaBoleta() {
		return fechaBoleta;
	}

	public void setFechaBoleta(String fechaBoleta) {
		this.fechaBoleta = fechaBoleta;
	}

	public String getHoraBoleta() {
		return horaBoleta;
	}

	public void setHoraBoleta(String horaBoleta) {
		this.horaBoleta = horaBoleta;
	}

	@Override
    public String toString() {
	    return "DatosRespuestaPagoTarjeta [datosPeticion=" + datosPeticion + ", codResult=" + codResult + ", msgRespuesta=" + msgRespuesta + ", ticket=" + ticket + ", tipoOp=" + tipoOp + ", importe="
	            + importe + ", AID=" + AID + ", tarjeta=" + tarjeta + ", marcaTarjeta=" + marcaTarjeta + ", descripcionMarcaTarjeta=" + descripcionMarcaTarjeta + ", titular=" + titular
	            + ", comercio=" + comercio + ", terminal=" + terminal + ", descBanco=" + descBanco + ", ARC=" + ARC + ", numOperacionBanco=" + numOperacionBanco + ", codAutorizacion="
	            + codAutorizacion + ", moneda=" + moneda + ", numOperacion=" + numOperacion + ", tipoLectura=" + tipoLectura + ", pedirFirma=" + pedirFirma + ", desgloses=" + desgloses
	            + ", adicionales=" + adicionales + ", importeDivisa=" + importeDivisa + ", codigoDivisa=" + codigoDivisa + ", exchangeRate=" + exchangeRate + ", comision=" + comision + ", DCC=" + DCC
	            + "]";
    }
	
}
