package com.comerzzia.api.v2.facturacionmagento.persistence.clientes;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.ClienteBean;

@Component
public interface ClientesMapper {

	ClienteBean selectByCif(@Param("uid_actividad") String uidActividad, @Param("cif") String cif);
	
	ClienteBean selectByCod(@Param("uid_actividad") String uidActividad, @Param("codCli") String codCli);
	
	Integer selectTaxGroup(@Param("uid_actividad") String uidActividad);
}
