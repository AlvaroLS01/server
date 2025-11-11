package com.comerzzia.api.v2.facturacionmagento.services.articulos;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.persistence.articulos.ArticulosMapper;

@Component
public class ArticulosService {

	protected static final Logger log = Logger.getLogger(ArticulosService.class);
	
	@Autowired
	protected ArticulosMapper articulosMapper;
	
	public String consultarCodBar(String uidActividad, String codArt) {
		log.debug("consultarCodBar() - Consultando codBar para artículo " + codArt);
		
		return articulosMapper.selectCodBar(uidActividad, codArt);
	}
	
}
