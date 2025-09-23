package com.comerzzia.api.v2.facturacionmagento.persistence.contadores.rangos;

import java.util.List;

import org.springframework.stereotype.Component;

import com.comerzzia.pos.persistence.core.config.configcontadores.rangos.ConfigContadorRangoBean;
import com.comerzzia.pos.persistence.core.config.configcontadores.rangos.ConfigContadorRangoExample;

@Component
public interface ConfigContadorRangosMapper {

	List<ConfigContadorRangoBean> selectByExample(ConfigContadorRangoExample example);
	
}
