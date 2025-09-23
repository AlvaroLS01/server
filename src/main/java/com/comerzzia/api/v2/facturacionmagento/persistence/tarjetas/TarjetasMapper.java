package com.comerzzia.api.v2.facturacionmagento.persistence.tarjetas;

import org.apache.ibatis.annotations.Param;

public interface TarjetasMapper {
	
	TarjetaBean selectByNumTarjeta(@Param("uid_instancia") String uidInstancia, @Param("numero_tarjeta") String numTarjetaFidelizado);
}
