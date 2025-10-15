package com.comerzzia.pos.services.ticket.cabecera;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.persistence.core.config.configcontadores.rangos.ConfigContadorRangoBean;
import com.comerzzia.pos.persistence.tarifas.TarifaBean;
import com.comerzzia.pos.services.articulos.tarifas.ArticulosTarifaService;
import com.comerzzia.pos.services.fiscaldata.FiscalData;
import com.comerzzia.pos.services.ticket.ITicket;
import com.comerzzia.pos.services.ticket.pagos.IPagoTicket;
import com.comerzzia.pos.util.format.FormatUtil;

@SuppressWarnings("rawtypes")
@Component
@Scope("prototype")
public class CabeceraTicket extends CabeceraTicketAbstract<SubtotalIvaTicket, ITotalesTicket<IPagoTicket>> {
	protected String fechaContable;

	protected BigDecimal cantidadArticulos;

	protected List<String> ticketsReferenciados;
	
	protected ConfigContadorRangoBean configContadorRango;
	
	protected FiscalData fiscalData;
	
	protected LinkedHashSet<String> tarifas;
	
	@Autowired
	ArticulosTarifaService articulosTarifaService;


	/*
	 * (non-Javadoc)
	 * @see
	 * com.comerzzia.pos.services.ticket.cabecera.ICabeceraTicket#inicializarCabecera(com.comerzzia.pos.services.ticket
	 * .ITicket)
	 */
	@Override
	public void inicializarCabecera(ITicket ticket) {
		super.inicializarCabecera(ticket);
						
		ticketsReferenciados = new ArrayList<>();
		
		cantidadArticulos = BigDecimal.ZERO;
	}


	public void addTicketReferenciado(String uidTicketVc) {
		ticketsReferenciados.add(uidTicketVc);
	}

	public List<String> getTicketsReferenciados() {
		return ticketsReferenciados;
	}

	public void setTicketsReferenciados(List<String> ventasCredito) {
		this.ticketsReferenciados = ventasCredito;
	}

	public BigDecimal getCantidadArticulos() {
		return cantidadArticulos;
	}

	public void setCantidadArticulos(BigDecimal cantidadArticulos) {
		this.cantidadArticulos = cantidadArticulos;
	}

	public String getCantidadArticulosAsString() {
		return FormatUtil.getInstance().formateaNumero(cantidadArticulos.abs());
	}

	public ConfigContadorRangoBean getConfigContadorRango() {
		return configContadorRango;
	}

	public void setConfigContadorRango(ConfigContadorRangoBean rango) {
		configContadorRango = rango;
	}

	public Date getFechaContable() {
		return FormatUtil.getInstance().desformateaFechaHoraTicket(fechaContable);
	}

	public void setFechaContable(Date fechaContable) {
		if(fechaContable != null) {
			this.fechaContable = FormatUtil.getInstance().formateaFechaHoraTicket(fechaContable);
		}
	}
		
	@Override
	public FiscalData getFiscalData() {
		return fiscalData;
	}

	@Override
	public void setFiscalData(FiscalData fiscalData) {
		this.fiscalData = fiscalData;
	}
	
	@Override
	public LinkedHashSet<String> getTarifas()  {
		// no se ha personalizado lista de tarifas, por lo tanto, se toma la lógica de siempre
		if (this.tarifas == null) {
			LinkedHashSet<String> tarifas = new LinkedHashSet<String>();
			
			// Opción 1: Buscamos tarifa con código igual al código del cliente
			tarifas.add(cliente.getCodCliente());
			
			// Opción 2: Buscamos tarifa con código igual al código de tarifa del cliente
			if (cliente.isTarifaAsignada()) {
				tarifas.add(cliente.getCodtar());
			}
			
			// Opción 3: Buscamos tarifa con código igual al código de tarifa del cliente de
			// la tienda
			if (getTienda().getCliente().isTarifaAsignada()) {
				tarifas.add(getTienda().getCliente().getCodtar());
				
				// Opción 4: Buscamos la tarifa PADRE de la tarifa con código igual al código de
				// tarifa del cliente de la tienda
				try {
					TarifaBean tarifaClienteTienda = articulosTarifaService.consultarTarifa(getTienda().getCliente().getCodtar());
					
					tarifas.add(tarifaClienteTienda.getCodTarifaPadre());
				} catch (Exception ignore) {
				}
			}
					
			// Opción 5: Buscamos tarifa general
			tarifas.add(TarifaBean.TARIFA_GENERAL);
			
			return tarifas;
		} else {
		   return this.tarifas;
		}
	}
	
	@Override
	public void setTarifas(LinkedHashSet tarifas) {
		this.tarifas = tarifas;		
	}

}