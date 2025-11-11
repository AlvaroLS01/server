package com.comerzzia.api.v2.facturacionmagento.services.promociones;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.persistence.promociones.PromocionBean;
import com.comerzzia.api.v2.facturacionmagento.persistence.promociones.PromocionesMapper;
import com.comerzzia.api.v2.facturacionmagento.services.articulos.ArticulosService;

@Component
public class PromocionesService {

	protected static final Logger log = Logger.getLogger(ArticulosService.class);

	@Autowired
	protected PromocionesMapper promocionesMapper;

	public long consultarIdTipoPromocion(String uidActividad, long idPromocion) {
		log.debug("consultarIdTipoPromocion() - Consultando idTipoPromocion para promoción con ID = " + idPromocion);

		return promocionesMapper.getIdPromotionType(uidActividad, idPromocion);
	}
	
	public PromocionBean consultarPromocion(String uidActividad, long idPromocion) {
		log.debug("consultarIdTipoPromocion() - Consultando idTipoPromocion para promoción con ID = " + idPromocion);
		
		return promocionesMapper.getPromocion(uidActividad, idPromocion);
	}
}
