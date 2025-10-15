package com.comerzzia.core.basketcalculator.persistencia.pasarelas.configuraciones;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.comerzzia.core.basketcalculator.model.pasarelas.configuraciones.ConfiguracionPasarelaBean;
import com.comerzzia.core.basketcalculator.model.pasarelas.configuraciones.ConfiguracionPasarelaExample;
import com.comerzzia.core.basketcalculator.model.pasarelas.configuraciones.ConfiguracionPasarelaKey;

public interface BasketCalculatorConfiguracionPasarelaMapper {
    int countByExample(ConfiguracionPasarelaExample example);

    int deleteByExample(ConfiguracionPasarelaExample example);

    int deleteByPrimaryKey(ConfiguracionPasarelaKey key);

    int insert(ConfiguracionPasarelaBean record);

    int insertSelective(ConfiguracionPasarelaBean record);

    List<ConfiguracionPasarelaBean> selectByExampleWithBLOBsWithRowbounds(ConfiguracionPasarelaExample example, RowBounds rowBounds);

    List<ConfiguracionPasarelaBean> selectByExampleWithBLOBs(ConfiguracionPasarelaExample example);

    List<ConfiguracionPasarelaBean> selectByExampleWithRowbounds(ConfiguracionPasarelaExample example, RowBounds rowBounds);

    List<ConfiguracionPasarelaBean> selectByExample(ConfiguracionPasarelaExample example);

    ConfiguracionPasarelaBean selectByPrimaryKey(ConfiguracionPasarelaKey key);

    int updateByExampleSelective(@Param("record") ConfiguracionPasarelaBean record, @Param("example") ConfiguracionPasarelaExample example);

    int updateByExampleWithBLOBs(@Param("record") ConfiguracionPasarelaBean record, @Param("example") ConfiguracionPasarelaExample example);

    int updateByExample(@Param("record") ConfiguracionPasarelaBean record, @Param("example") ConfiguracionPasarelaExample example);

    int updateByPrimaryKeySelective(ConfiguracionPasarelaBean record);

    int updateByPrimaryKeyWithBLOBs(ConfiguracionPasarelaBean record);

    int updateByPrimaryKey(ConfiguracionPasarelaBean record);
}