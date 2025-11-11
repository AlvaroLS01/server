package com.comerzzia.api.v2.facturacionmagento.persistence.contadores;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.persistence.core.contadores.ContadorBean;
import com.comerzzia.pos.persistence.core.contadores.ContadorExample;
import com.comerzzia.pos.persistence.core.contadores.ContadorKey;

@Component
public interface ContadoresMapper {

	ContadorBean selectByPrimaryKey(ContadorKey key);
	
	int insert(ContadorBean record);

	int updateByExample(@Param("record") ContadorBean record, @Param("example") ContadorExample example);
	
	int updateByPrimaryKey(ContadorBean record);
	
}
