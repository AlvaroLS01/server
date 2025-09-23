package com.comerzzia.api.v2.facturacionmagento.persistence.contadores.config.parametros;

import java.util.List;

import org.springframework.stereotype.Component;

import com.comerzzia.pos.persistence.core.config.configcontadores.parametros.ConfigContadorParametroBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.parametros.ConfigContadorParametroExample;

@Component
public interface ConfigContadoresParametroMapper {

	List<ConfigContadorParametroBean> selectByExample(ConfigContadorParametroExample example);
	
}
