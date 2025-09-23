package com.comerzzia.api.v2.facturacionmagento.persistence.mediospago;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.persistence.mediosPagos.MedioPagoBean;

@Component
public interface MediosPagoMapper {

	MedioPagoBean selectByCod(@Param("uid_actividad") String uidActividad, @Param("cod_medio_pago") String codMedioPago);
	
}
