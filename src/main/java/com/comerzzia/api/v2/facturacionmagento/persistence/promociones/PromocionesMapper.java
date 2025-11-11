package com.comerzzia.api.v2.facturacionmagento.persistence.promociones;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface PromocionesMapper {

	long getIdPromotionType(@Param("uid_actividad") String uidActividad, @Param("id_promocion") long idPromocion);
	PromocionBean getPromocion(@Param("uid_actividad") String uidActividad, @Param("id_promocion") long idPromocion);
	
}
