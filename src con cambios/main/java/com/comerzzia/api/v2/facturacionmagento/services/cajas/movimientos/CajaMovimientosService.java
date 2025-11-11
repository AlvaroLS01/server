package com.comerzzia.api.v2.facturacionmagento.services.cajas.movimientos;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.movimientos.CajaMovimientoBean;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.movimientos.CajaMovimientoKey;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.movimientos.CajaMovimientosMapper;

@Component
public class CajaMovimientosService {

	protected static final Logger log = Logger.getLogger(CajaMovimientosService.class);
	
	@Autowired
	protected CajaMovimientosMapper cajaMovimientosMapper;

	public List<CajaMovimientoBean> consultarMovimientos(String uidActividad, String uidDiarioCaja) {
		log.debug("consultarMovimientos() - Consultando movimientos para uidDiarioCaja:" + uidDiarioCaja);
		
		CajaMovimientoKey key = new CajaMovimientoKey();
		key.setUidActividad(uidActividad);
		key.setUidDiarioCaja(uidDiarioCaja);
		List<CajaMovimientoBean> movimientos = cajaMovimientosMapper.selectByPrimaryKey(key);
		
		return movimientos;
	}

}
