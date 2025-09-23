package com.comerzzia.api.v2.facturacionmagento.services.contadores.rangos;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.persistence.contadores.rangos.ConfigContadorRangosMapper;
import com.comerzzia.core.servicios.config.configContadores.ContadoresConfigException;
import com.comerzzia.pos.persistence.core.config.configcontadores.rangos.ConfigContadorRangoBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.rangos.ConfigContadorRangoExample;

@Component
public class ConfigContadorRangosService {

	protected static final Logger log = Logger.getLogger(ConfigContadorRangosService.class);

	@Autowired
	protected ConfigContadorRangosMapper configContadorRangosMapper;

	public List<ConfigContadorRangoBean> consultar(ConfigContadorRangoExample example) throws ContadoresConfigException {
		log.debug("consultar() - Consultando rangos ");
		try {
			return configContadorRangosMapper.selectByExample(example);
		}
		catch (Exception e) {
			log.error("consultar() - " + e.getMessage());
			String mensaje = "Error al consultar los rangos de un contador: " + e.getMessage();
			throw new ContadoresConfigException(mensaje, e);
		}
	}

	public List<ConfigContadorRangoBean> consultar(String idContador) throws ContadoresConfigException {
		log.debug("consultar() - Consultando rangos del contador " + idContador);
		try {
			ConfigContadorRangoExample configContadorRangoExample = new ConfigContadorRangoExample();
			configContadorRangoExample.or().andIdContadorEqualTo(idContador);

			return configContadorRangosMapper.selectByExample(configContadorRangoExample);
		}
		catch (Exception e) {
			log.error("consultar() - " + e.getMessage());
			String mensaje = "Error al consultar los rangos de un contador: " + e.getMessage();
			throw new ContadoresConfigException(mensaje, e);
		}
	}

}
