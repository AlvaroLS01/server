package com.comerzzia.pos.persistence.articulos.tarifas;

import java.util.List;

public interface TarifaDetalleMapper {
    List<TarifaDetalleBean> selectByExample(TarifaDetalleExample example);
}