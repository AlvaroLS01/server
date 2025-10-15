package com.comerzzia.pos.persistence.promociones.detalle;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PromocionDetalleMapper {
      List<PromocionDetalleBean> selectByPromotionId(@Param("uidActividad") String uidActividad, @Param("promotionId") Long promotionId);
}