package com.comerzzia.omnichannel.service.item;

import com.comerzzia.omnichannel.domain.dto.item.ItemDTO;
import com.comerzzia.omnichannel.domain.entity.item.ItemEntity;

public interface ItemService {

    ItemEntity selectByPrimaryKey(String activityUid, String itemCode);

    ItemDTO selectDTOByPrimaryKey(String activityUid, String itemCode);

    ItemDTO selectDTOByBarcode(String activityUid, String barcode);

}