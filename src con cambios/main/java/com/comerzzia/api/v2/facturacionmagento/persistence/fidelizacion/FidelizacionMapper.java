package com.comerzzia.api.v2.facturacionmagento.persistence.fidelizacion;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.FidelizacionBean;

@Component
public interface FidelizacionMapper {

	FidelizacionBean selectById(@Param("uid_instancia") String uidInstancia, @Param("id_fidelizado") Long idFidelizado);

	FidelizacionBean selectByCardCode(@Param("uid_instancia") String uidInstancia, @Param("id_fidelizado") Long idFidelizado, @Param("cod_tarjeta") String codTarjeta);
}
