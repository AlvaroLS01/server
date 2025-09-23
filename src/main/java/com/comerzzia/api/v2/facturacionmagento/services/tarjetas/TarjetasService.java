package com.comerzzia.api.v2.facturacionmagento.services.tarjetas;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.persistence.tarjetas.TarjetaBean;
import com.comerzzia.api.v2.facturacionmagento.persistence.tarjetas.TarjetasMapper;
import com.comerzzia.api.v2.facturacionmagento.services.fidelizacion.FidelizacionService;

@Component
public class TarjetasService {

protected static final Logger log = Logger.getLogger(FidelizacionService.class);
	
	@Autowired
	protected TarjetasMapper tarjetasMapper;
	
	public TarjetaBean consultar(String uidInstancia, String numTarjetaFidelizado) throws Exception {
		log.debug("consultar() - Consultando tarjeta : " + numTarjetaFidelizado);
		
		TarjetaBean tarjetaBean = null;
		tarjetaBean = tarjetasMapper.selectByNumTarjeta(uidInstancia, numTarjetaFidelizado);
		
		if(tarjetaBean == null) {
			String msg = "Error consultando la tarjeta con numero: " + numTarjetaFidelizado;
			log.error("consultar() - " + msg);
			throw new Exception(msg);
		}
		
		return tarjetaBean;
	}
}
