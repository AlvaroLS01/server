package com.comerzzia.omnichannel.service.basket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.api.core.service.exception.NotFoundException;
import com.comerzzia.core.basketcalculator.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.entity.basket.BasketEntity;
import com.comerzzia.omnichannel.domain.entity.basket.BasketKey;
import com.comerzzia.omnichannel.repository.basket.BasketMapper;

@Service
public class BasketServiceImpl implements BasketService {
	@Autowired
	protected BasketMapper mapper;

	@Override
	public BasketEntity selectByPrimaryKey(IDatosSesion datosSesion, BasketKey key) {
		key.setUidActividad(datosSesion.getUidActividad());

		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public void insert(IDatosSesion datosSesion, BasketEntity record) {
		record.setUidActividad(datosSesion.getUidActividad());

		mapper.insert(record);
	}

	@Override
	public void updateByPrimaryKey(IDatosSesion datosSesion, BasketEntity record) throws NotFoundException {
		record.setUidActividad(datosSesion.getUidActividad());

		if (mapper.updateByPrimaryKeyWithBLOBs(record) == 0) {
			throw new NotFoundException();
		}
	}

	@Override
	public void deleteByPrimaryKey(IDatosSesion datosSesion, BasketEntity record) throws NotFoundException {
		record.setUidActividad(datosSesion.getUidActividad());
		
		if (mapper.deleteByPrimaryKey(record) == 0) {
			throw new NotFoundException();
		}
	}
}
