package com.comerzzia.pos.persistence.articulos.categorizaciones;

import org.apache.ibatis.annotations.Param;

public interface CategorizacionMapper {
    CategorizacionBean selectByPrimaryKey(@Param("activityUid") String activityUid, @Param("categoryCode") String categoryCode);
}