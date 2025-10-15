package com.comerzzia.omnichannel.repository.basket;

import com.comerzzia.omnichannel.domain.entity.basket.BasketEntity;
import com.comerzzia.omnichannel.domain.entity.basket.BasketKey;

public interface BasketMapper {
    int deleteByPrimaryKey(BasketKey key);

    int insert(BasketEntity record);

    BasketEntity selectByPrimaryKey(BasketKey key);

    int updateByPrimaryKeySelective(BasketEntity record);

    int updateByPrimaryKeyWithBLOBs(BasketEntity record);

    int updateByPrimaryKey(BasketEntity record);
}