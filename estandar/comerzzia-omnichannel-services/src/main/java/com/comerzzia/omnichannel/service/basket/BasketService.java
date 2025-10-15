package com.comerzzia.omnichannel.service.basket;

import com.comerzzia.api.core.service.exception.NotFoundException;
import com.comerzzia.core.basketcalculator.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.entity.basket.BasketEntity;
import com.comerzzia.omnichannel.domain.entity.basket.BasketKey;

public interface BasketService {

	BasketEntity selectByPrimaryKey(IDatosSesion datosSesion, BasketKey key);

	void insert(IDatosSesion datosSesion, BasketEntity record);

	void updateByPrimaryKey(IDatosSesion datosSesion, BasketEntity record) throws NotFoundException;

	void deleteByPrimaryKey(IDatosSesion datosSesion, BasketEntity record) throws NotFoundException;

}