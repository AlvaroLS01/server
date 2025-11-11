package com.comerzzia.api.v2.facturacionmagento.persistence.tarifas;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface TarifasMapper {

	Tarifa selectTarifaArt(@Param("uid_actividad") String uidActividad, @Param("cod_alm") String codAlm, @Param("cod_art") String codArt);
	
}
