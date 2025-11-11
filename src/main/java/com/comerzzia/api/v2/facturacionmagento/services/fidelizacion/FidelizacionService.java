package com.comerzzia.api.v2.facturacionmagento.services.fidelizacion;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.persistence.fidelizacion.FidelizacionMapper;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.FidelizacionBean;

@Component
public class FidelizacionService {

	protected static final Logger log = Logger.getLogger(FidelizacionService.class);
	
	@Autowired
	protected FidelizacionMapper fidelizacionMapper;
	
	public FidelizacionBean consultar(String uidInstancia, Long idFidelizado) throws Exception {
		log.debug("consultar() - Consultando fidelizado con ID: " + idFidelizado);
		
		FidelizacionBean fidelizacionBean = null;
		fidelizacionBean = fidelizacionMapper.selectById(uidInstancia, idFidelizado);
		
		if(fidelizacionBean == null) {
			String msg = "Error consultando fidelizado con ID " + idFidelizado;
			log.error("consultar() - " + msg);
			throw new Exception(msg);
		}
		
		return fidelizacionBean;
	}
	
	public FidelizacionBean consultar(String uidInstancia, Long idFidelizado, String codTarjeta) throws Exception {
		log.debug("consultar() - Consultando fidelizado con ID: " + idFidelizado + " y codTarjeta: " + codTarjeta);
		
		FidelizacionBean fidelizacionBean = null;
		fidelizacionBean = fidelizacionMapper.selectByCardCode(uidInstancia, idFidelizado, codTarjeta);
		
		if(fidelizacionBean == null) {
			String msg = "Error consultando fidelizado con ID " + idFidelizado + " y codTarjeta " + codTarjeta;
			log.error("consultar() - " + msg);
			throw new Exception(msg);
		}
		
		return fidelizacionBean;
	}
	
}
