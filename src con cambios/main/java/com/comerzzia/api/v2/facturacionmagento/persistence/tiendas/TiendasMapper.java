package com.comerzzia.api.v2.facturacionmagento.persistence.tiendas;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.Tienda;

@Component
public interface TiendasMapper {
	
	public Tienda selectById(@Param("uid_actividad") String uidActividad, @Param("idTienda") String idTienda);
	
}
