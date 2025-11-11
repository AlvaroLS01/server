package com.comerzzia.api.v2.facturacionmagento.persistence.motivos;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MotivoMapper {
    long countByExample(MotivoExample example);

    int deleteByExample(MotivoExample example);

    int deleteByPrimaryKey(MotivoKey key);

    int insert(Motivo record);

    int insertSelective(Motivo record);

    List<Motivo> selectByExampleWithRowbounds(MotivoExample example, RowBounds rowBounds);

    List<Motivo> selectByExample(MotivoExample example);

    Motivo selectByPrimaryKey(MotivoKey key);

    int updateByExampleSelective(@Param("record") Motivo record, @Param("example") MotivoExample example);

    int updateByExample(@Param("record") Motivo record, @Param("example") MotivoExample example);

    int updateByPrimaryKeySelective(Motivo record);

    int updateByPrimaryKey(Motivo record);
}