package com.comerzzia.pos.persistence.tarifas;

import java.util.List;

public interface TarifaMapper {    
    List<TarifaBean> selectByExample(TarifaExample example);

    TarifaBean selectByPrimaryKey(TarifaKey key);
}