package com.comerzzia.api.v2.facturacionmagento.services.tiendas;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.persistence.tiendas.TiendasMapper;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.Tienda;
import com.comerzzia.omnichannel.model.documents.sales.ticket.pagos.PagoTicket;

@Component
public class TiendasService {

	protected static final Logger log = Logger.getLogger(TiendasService.class);
	
	@Autowired
	private TiendasMapper tiendasMapper;
	
	public Tienda consultarTienda(String uidActividad, String codTienda) throws Exception {
		log.debug("consultarTienda() - Consultando tienda " + codTienda);
		
		Tienda tienda = tiendasMapper.selectById(uidActividad, codTienda);
		if(tienda == null) {
			String msg = "No se ha encontrado ninguna tienda con código " + codTienda;
			log.error("consultarTienda() - " + msg);
			throw new Exception(msg);
		}
		
		return tienda;
	}
	
	public PagoTicket selectMedioPagoDefecto(String uidActividad, String codTienda) throws Exception {
		log.debug("selectMedioPagoDefecto() - Consultando medio pago por defecto de la tienda " + codTienda);
		
		PagoTicket pagoTicket = tiendasMapper.selectMedioPagoDefecto(uidActividad, codTienda);
		if(pagoTicket == null) {
			String msg = "No se ha encontrado ninguna tienda con código " + codTienda;
			log.error("selectMedioPagoDefecto() - " + msg);
			throw new Exception(msg);
		}
		
		return pagoTicket;
	}
	
}
