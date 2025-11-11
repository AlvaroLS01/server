package com.comerzzia.api.v2.facturacionmagento.persistence.contadores.config;

import org.springframework.stereotype.Component;

import com.comerzzia.pos.persistence.core.config.configcontadores.ConfigContadorBean;

@Component
public interface ConfigContadoresMapper {

	public ConfigContadorBean selectByPrimaryKey(String idContador);
	
}
