package com.comerzzia.api.v2.facturacionmagento.services.tiendas;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.persistence.tiendas.TiendasMapper;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.Tienda;

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
	
}
