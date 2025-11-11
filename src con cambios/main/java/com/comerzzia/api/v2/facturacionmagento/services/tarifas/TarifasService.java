package com.comerzzia.api.v2.facturacionmagento.services.tarifas;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.persistence.tarifas.Tarifa;
import com.comerzzia.api.v2.facturacionmagento.persistence.tarifas.TarifasMapper;

@Component
public class TarifasService {
	
	@Autowired
	protected TarifasMapper tarifasMapper;

	protected static final Logger log = Logger.getLogger(TarifasService.class);
	
	public Tarifa consultarTarifaActivaArticulo(String uidActividad, String codAlm, String codArt) throws Exception {
		log.debug("consultarTarifaActivaArticulo() - Consultando tarifa activa para articulo " + codArt);
		
		Tarifa tarifa = null;
		tarifa = tarifasMapper.selectTarifaArt(uidActividad, codAlm, codArt);
		
		if(tarifa == null) {
			String msg = "Error consultando tarifa activa para artículo " + codArt;
			log.error("consultarTarifaActivaArticulo() - " + msg);
			throw new Exception(msg);
		}
		
		return tarifa;
	}
	
}
