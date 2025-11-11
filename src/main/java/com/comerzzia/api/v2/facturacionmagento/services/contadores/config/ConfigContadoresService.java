package com.comerzzia.api.v2.facturacionmagento.services.contadores.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.persistence.contadores.config.ConfigContadoresMapper;
import com.comerzzia.core.servicios.config.configContadores.ContadoresConfigNotFoundException;
import com.comerzzia.pos.persistence.core.config.configcontadores.ConfigContadorBean;

@Component
public class ConfigContadoresService {
	
	protected static final Logger log = Logger.getLogger(ConfigContadoresService.class);

	@Autowired
	protected ConfigContadoresMapper configContadoresMapper;

	public ConfigContadorBean consultar(String idContador) throws ContadoresConfigNotFoundException {
		log.debug("consultar() - Consultando configuración del rango con ID: " + idContador);
		
		ConfigContadorBean configContador = configContadoresMapper.selectByPrimaryKey(idContador);
		
		if (configContador == null) {
			String msg = "No se ha encontrado el contador con identificador: " + idContador;
			log.info("consultar() - " + msg);
			throw new ContadoresConfigNotFoundException(msg);
		}
		
		return configContador;
	}

}
