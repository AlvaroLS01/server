package com.comerzzia.api.v2.facturacionmagento.persistence.cajas;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

@Component
public interface CajaMapper {
    int countByExample(CajaExample example);

    int deleteByExample(CajaExample example);

    int deleteByPrimaryKey(CajaKey key);

    int insert(CajaBean record);

    int insertSelective(CajaBean record);

    List<CajaBean> selectByExampleWithRowbounds(CajaExample example, RowBounds rowBounds);

    List<CajaBean> selectByExample(CajaExample example);

    CajaBean selectByPrimaryKey(CajaKey key);

    int updateByExampleSelective(@Param("record") CajaBean record, @Param("example") CajaExample example);

    int updateByExample(@Param("record") CajaBean record, @Param("example") CajaExample example);

    int updateByPrimaryKeySelective(CajaBean record);

    int updateByPrimaryKey(CajaBean record);
    
    int cierreCajaDateTimeByPrimaryKey(CajaBean record);
}