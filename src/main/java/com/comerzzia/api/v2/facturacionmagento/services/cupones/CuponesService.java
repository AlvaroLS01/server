package com.comerzzia.api.v2.facturacionmagento.services.cupones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.comerzzia.api.v2.facturacionmagento.persistence.cupones.CuponBean;
import com.comerzzia.api.v2.facturacionmagento.persistence.cupones.CuponesMapper;

import org.apache.log4j.Logger;

@Component
public class CuponesService {

	protected static final Logger log = Logger.getLogger(CuponesService.class);

	@Autowired
	protected CuponesMapper cuponesMapper;
	
	public CuponBean consultarCupon(String uidActividad, String couponCode) {
		log.debug("consultarCupon() - Consultando cupon con codigo = " + couponCode);
		
		return cuponesMapper.getCupon(uidActividad, couponCode);
	}
	
}
