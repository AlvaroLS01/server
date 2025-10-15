package com.comerzzia.core.basketcalculator.persistencia.pasarelas.tipos;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.comerzzia.core.basketcalculator.model.pasarelas.tipos.TipoPasarelaBean;
import com.comerzzia.core.basketcalculator.model.pasarelas.tipos.TipoPasarelaExample;
import com.comerzzia.core.basketcalculator.model.pasarelas.tipos.TipoPasarelaKey;

public interface BasketCalculatorTipoPasarelaMapper {
    int countByExample(TipoPasarelaExample example);

    int deleteByExample(TipoPasarelaExample example);

    int deleteByPrimaryKey(TipoPasarelaKey key);

    int insert(TipoPasarelaBean record);

    int insertSelective(TipoPasarelaBean record);

    List<TipoPasarelaBean> selectByExampleWithRowbounds(TipoPasarelaExample example, RowBounds rowBounds);

    List<TipoPasarelaBean> selectByExample(TipoPasarelaExample example);

    TipoPasarelaBean selectByPrimaryKey(TipoPasarelaKey key);

    int updateByExampleSelective(@Param("record") TipoPasarelaBean record, @Param("example") TipoPasarelaExample example);

    int updateByExample(@Param("record") TipoPasarelaBean record, @Param("example") TipoPasarelaExample example);

    int updateByPrimaryKeySelective(TipoPasarelaBean record);

    int updateByPrimaryKey(TipoPasarelaBean record);
}