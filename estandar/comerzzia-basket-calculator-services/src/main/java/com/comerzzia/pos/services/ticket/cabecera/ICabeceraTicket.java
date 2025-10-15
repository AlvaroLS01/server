package com.comerzzia.pos.services.ticket.cabecera;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import com.comerzzia.pos.persistence.clientes.ClienteBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.rangos.ConfigContadorRangoBean;
import com.comerzzia.pos.persistence.core.documentos.tipos.TipoDocumentoBean;
import com.comerzzia.pos.persistence.core.empresas.EmpresaBean;
import com.comerzzia.pos.persistence.core.usuarios.UsuarioBean;
import com.comerzzia.pos.persistence.fidelizacion.FidelizacionBean;
import com.comerzzia.pos.persistence.giftcard.GiftCardBean;
import com.comerzzia.pos.services.core.tiendas.Tienda;
import com.comerzzia.pos.services.fiscaldata.FiscalData;
import com.comerzzia.pos.services.ticket.ITicket;
import com.comerzzia.pos.services.ticket.Ticket;
import com.comerzzia.pos.services.ticket.pagos.IPagoTicket;

@SuppressWarnings("rawtypes")
public interface ICabeceraTicket<S extends ISubtotalIvaTicket,T extends ITotalesTicket<IPagoTicket>> {

	public abstract void inicializarCabecera(ITicket ticket);

    public abstract ITicket getTicket();

	public abstract void setTicket(Ticket ticket);

	public abstract String getUidTicket();
	
	public abstract void setUidTicket(String uidTicket);

	public abstract String getCodCaja();

	public abstract Tienda getTienda();

	public abstract String getUidActividad();

	public abstract String getUidDiarioCaja();

	public abstract void setUidDiarioCaja(String uidDiarioCaja);

	public abstract EmpresaBean getEmpresa();

	T getTotales();

	public abstract Date getFecha();

	public abstract Long getIdTicket();

	public abstract String getIdTicketAsString();

	public abstract ClienteBean getCliente();

	public abstract UsuarioBean getCajero();

	public abstract void setFecha(Date fecha);

	public abstract void setIdTicket(Long idTicket);

	public abstract void setCliente(ClienteBean cliente);

	public abstract void setCajero(UsuarioBean cajero);

	// Setters para el unmarshall
	public abstract void setTienda(Tienda tienda);

	public abstract void setEmpresa(EmpresaBean empresa);

	public abstract List<S> getSubtotalesIva();

	public abstract String getFechaAsLocale();

	/**
	 * Vuelve a establecer los parámetros del ticket que dependen de la caja y tienda en la que se va a completar el ticket
	 */
	public abstract String getUidTicketEnlace();

	public abstract void setUidTicketEnlace(String uidTicketEnlace);

	public abstract void setDocumento(TipoDocumentoBean doc);

	public abstract Long getTipoDocumento();

	public abstract void setTipoDocumento(Long tipoDocumento);

	public abstract String getCodTipoDocumento();

	public abstract void setCodTipoDocumento(String codTipoDocumento);

	public abstract String getFormatoImpresion();

	public abstract void setFormatoImpresion(String formatoImpresion);

	public abstract String getFormatoImpresionTicketRegalo();

	public abstract void setFormatoImpresionTicketRegalo(
			String formatoImpresionTicketRegalo);

	public abstract String getSerieTicket();

	public abstract void setSerieTicket(String serieTicket);

	public abstract String getCodTicket();

	public abstract void setCodTicket(String codTicket);

	public abstract DatosDocumentoOrigenTicket getDatosDocOrigen();

	public abstract void setDatosDocOrigen(
			DatosDocumentoOrigenTicket datosDocOrigen);

	public abstract FirmaTicket getFirma();

	public abstract void setFirma(FirmaTicket firma);
	
	public abstract void setLocalizador(String localizador);
	
	public abstract String getLocalizador();
		

	/**
	 * @return the datosFidelizado
	 */
	public abstract FidelizacionBean getDatosFidelizado();

	/**
	 * @param datosFidelizado the datosFidelizado to set
	 */
	//public abstract void setDatosFidelizado(FidelizacionBean datosFidelizado);

	public abstract void setDatosFidelizado(FidelizacionBean tarjeta);

	public abstract void setDatosFidelizado(String numTarjeta);

	public abstract void setTarjetaRegalo(TarjetaRegaloTicket tarjetaRegalo);

	public abstract TarjetaRegaloTicket getTarjetaRegalo();

	public abstract void agnadirTarjetaRegalo(GiftCardBean tarjeta);

	public abstract String getDesTipoDocumento();

	public abstract ClienteBean getDatosEnvio();

	public abstract void setDatosEnvio(ClienteBean datosEnvio);
	
    public BigDecimal getCantidadArticulos();
	
    public void setCantidadArticulos(BigDecimal cantidadArticulos);
    
    public String getCantidadArticulosAsString();
    
    public Boolean esVenta();

	void setConfigContadorRango(ConfigContadorRangoBean rango);

	ConfigContadorRangoBean getConfigContadorRango();
	
	Date getFechaContable();
	
	void setFechaContable(Date fechaContable);
	
	FiscalData getFiscalData();
	
	void setFiscalData(FiscalData fiscalData);

	LinkedHashSet<String> getTarifas();

	void setTarifas(LinkedHashSet<String> tarifas);

	void setTotales(T total);
	
}