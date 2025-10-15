package com.comerzzia.pos.persistence.core.usuarios.almacenes;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface POSAlmacenUsuarioMapper {
    int countByExample(AlmacenUsuarioExample example);

    int deleteByExample(AlmacenUsuarioExample example);

    int deleteByPrimaryKey(AlmacenUsuarioKey key);

    int insert(AlmacenUsuarioKey record);

    int insertSelective(AlmacenUsuarioKey record);

    List<AlmacenUsuarioKey> selectByExampleWithRowbounds(AlmacenUsuarioExample example, RowBounds rowBounds);

    List<AlmacenUsuarioKey> selectByExample(AlmacenUsuarioExample example);

    int updateByExampleSelective(@Param("record") AlmacenUsuarioKey record, @Param("example") AlmacenUsuarioExample example);

    int updateByExample(@Param("record") AlmacenUsuarioKey record, @Param("example") AlmacenUsuarioExample example);
}