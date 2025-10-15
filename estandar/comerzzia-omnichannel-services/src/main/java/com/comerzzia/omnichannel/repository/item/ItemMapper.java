package com.comerzzia.omnichannel.repository.item;

import org.apache.ibatis.annotations.Param;

import com.comerzzia.omnichannel.domain.dto.item.ItemDTO;
import com.comerzzia.omnichannel.domain.entity.item.ItemEntity;

public interface ItemMapper {
    ItemEntity selectByPrimaryKey(@Param("activityUid") String activityUid, @Param("itemCode") String itemCode);
    
    ItemDTO selectDTOByPrimaryKey(@Param("activityUid") String activityUid, @Param("itemCode") String itemCode);
    
    ItemDTO selectDTOByBarcode(@Param("activityUid") String activityUid, @Param("barcode") String barcode);
}