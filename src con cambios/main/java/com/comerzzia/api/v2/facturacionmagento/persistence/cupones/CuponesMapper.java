package com.comerzzia.api.v2.facturacionmagento.persistence.cupones;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface CuponesMapper {

	CuponBean getCupon(@Param("uid_actividad") String uidActividad, @Param("coupon_code") String couponCode);
	
}
