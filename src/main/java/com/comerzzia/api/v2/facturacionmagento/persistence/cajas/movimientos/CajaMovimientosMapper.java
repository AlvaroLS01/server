package com.comerzzia.api.v2.facturacionmagento.persistence.cajas.movimientos;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface CajaMovimientosMapper {

	List<CajaMovimientoBean> selectByPrimaryKey(CajaMovimientoKey key);
	Integer selectMaximaLineaMovimiento(CajaMovimientoExample example);
	int insert(CajaMovimientoBean record);
}
