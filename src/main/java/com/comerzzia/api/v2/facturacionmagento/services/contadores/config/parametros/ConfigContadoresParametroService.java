package com.comerzzia.api.v2.facturacionmagento.services.contadores.config.parametros;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.persistence.contadores.config.parametros.ConfigContadoresParametroMapper;
import com.comerzzia.pos.persistence.core.config.configcontadores.parametros.ConfigContadorParametroBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.parametros.ConfigContadorParametroExample;
import com.comerzzia.pos.services.core.config.configContadores.parametros.ConfigContadoresParametrosException;

@Component
public class ConfigContadoresParametroService {

	protected static Logger log = Logger.getLogger(ConfigContadoresParametroService.class);
	
	@Autowired
	protected ConfigContadoresParametroMapper configContadoresParametroMapper;
	
	public Map<String, ConfigContadorParametroBean> consultarMap(String idContador) throws ConfigContadoresParametrosException {

		try {
			log.debug("consultar() - Consultando parÃ¡metro del contador " + idContador);

			ConfigContadorParametroExample filtro = new ConfigContadorParametroExample();
			filtro.or().andIdContadorEqualTo(idContador);

			Map<String, ConfigContadorParametroBean> mapParametros = new HashMap<String, ConfigContadorParametroBean>();
			List<ConfigContadorParametroBean> listConfigContadorParametros = configContadoresParametroMapper.selectByExample(filtro);
			if(listConfigContadorParametros != null) {
				for(ConfigContadorParametroBean configParametro: listConfigContadorParametros) {
					mapParametros.put(configParametro.getParametro(), configParametro);
				}
			}
			
			return mapParametros;
		}
		catch (Exception e) {
			log.error("consultar() - " + e.getMessage());
			String mensaje = "Error al consultar los parÃ¡metros del contador " + idContador + ": " + e.getMessage();

			throw new ConfigContadoresParametrosException(mensaje, e);
		}
	}

}
