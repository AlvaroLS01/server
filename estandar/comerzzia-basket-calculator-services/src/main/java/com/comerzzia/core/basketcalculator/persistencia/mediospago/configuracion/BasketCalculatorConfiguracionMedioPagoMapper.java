package com.comerzzia.core.basketcalculator.persistencia.mediospago.configuracion;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.comerzzia.core.basketcalculator.model.mediospago.configuracion.ConfiguracionMedioPagoBean;
import com.comerzzia.core.basketcalculator.model.mediospago.configuracion.ConfiguracionMedioPagoExample;
import com.comerzzia.core.basketcalculator.model.mediospago.configuracion.ConfiguracionMedioPagoKey;

public interface BasketCalculatorConfiguracionMedioPagoMapper {
    int countByExample(ConfiguracionMedioPagoExample example);

    int deleteByExample(ConfiguracionMedioPagoExample example);

    int deleteByPrimaryKey(ConfiguracionMedioPagoKey key);

    int insert(ConfiguracionMedioPagoBean record);

    int insertSelective(ConfiguracionMedioPagoBean record);

    List<ConfiguracionMedioPagoBean> selectByExampleWithRowbounds(ConfiguracionMedioPagoExample example, RowBounds rowBounds);

    List<ConfiguracionMedioPagoBean> selectByExample(ConfiguracionMedioPagoExample example);

    ConfiguracionMedioPagoBean selectByPrimaryKey(ConfiguracionMedioPagoKey key);

    int updateByExampleSelective(@Param("record") ConfiguracionMedioPagoBean record, @Param("example") ConfiguracionMedioPagoExample example);

    int updateByExample(@Param("record") ConfiguracionMedioPagoBean record, @Param("example") ConfiguracionMedioPagoExample example);

    int updateByPrimaryKeySelective(ConfiguracionMedioPagoBean record);

    int updateByPrimaryKey(ConfiguracionMedioPagoBean record);

    ConfiguracionMedioPagoBean selectFromViewByPrimaryKey(ConfiguracionMedioPagoKey key);

    List<ConfiguracionMedioPagoBean> selectFromViewByExampleWithRowbounds(ConfiguracionMedioPagoExample example, RowBounds rowBounds);

    List<ConfiguracionMedioPagoBean> selectFromViewByExample(ConfiguracionMedioPagoExample example);
}