package com.comerzzia.pos.persistence.mediosPagos;

import java.util.List;

public interface MedioPagoMapper {
	List<MedioPagoBean> selectByExample(MedioPagoExample example);

	MedioPagoBean selectByPrimaryKey(MedioPagoKey key);
}