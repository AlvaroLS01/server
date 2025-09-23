package com.comerzzia.api.v2.facturacionmagento.services.cajas.lineas;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.lineas.CajaLineaRecuentoBean;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.lineas.CajaLineaRecuentoExample;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.lineas.CajaLineaRecuentoMapper;

@Component
public class CajaLineasService {
	
	protected static final Logger log = Logger.getLogger(CajaLineasService.class);
	
	@Autowired
	protected CajaLineaRecuentoMapper cajaLineaRecuentoMapper;

	public List<CajaLineaRecuentoBean> consultarLineasRecuento(String uidActividad, String uidDiarioCaja) {
		log.debug("consultarLineasRecuento() - Consultando lineas recuento con uidDiarioCaja " + uidDiarioCaja);

		CajaLineaRecuentoExample example = new CajaLineaRecuentoExample();
		example.or().andUidActividadEqualTo(uidActividad).andUidDiarioCajaEqualTo(uidDiarioCaja);
		
		return cajaLineaRecuentoMapper.selectByExample(example);
	}

}
