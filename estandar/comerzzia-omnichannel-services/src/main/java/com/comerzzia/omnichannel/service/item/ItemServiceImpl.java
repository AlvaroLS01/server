package com.comerzzia.omnichannel.service.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.omnichannel.domain.dto.item.ItemDTO;
import com.comerzzia.omnichannel.domain.entity.item.ItemEntity;
import com.comerzzia.omnichannel.repository.item.ItemMapper;

@Service
public class ItemServiceImpl implements ItemService {    
    @Autowired
    protected ItemMapper mapper;
        
    @Override
    public ItemEntity selectByPrimaryKey(String activityUid, String itemCode) {
    	return mapper.selectByPrimaryKey(activityUid, itemCode);
    }    
    
    @Override
    public ItemDTO selectDTOByPrimaryKey(String activityUid, String itemCode) {
    	return mapper.selectDTOByPrimaryKey(activityUid, itemCode);
    }  
    
    @Override
    public ItemDTO selectDTOByBarcode(String activityUid, String barcode) {
    	return mapper.selectDTOByBarcode(activityUid, barcode);
    }
           
}
