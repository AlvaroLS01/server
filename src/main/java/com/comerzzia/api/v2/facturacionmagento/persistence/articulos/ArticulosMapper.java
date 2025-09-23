package com.comerzzia.api.v2.facturacionmagento.persistence.articulos;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface ArticulosMapper {

	String selectCodBar(@Param("uid_actividad") String uidActividad, @Param("cod_art") String codArt);
	
}
