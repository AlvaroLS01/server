package com.comerzzia.omnichannel.repository.paytransaction;

import org.apache.ibatis.annotations.Param;

import com.comerzzia.omnichannel.domain.entity.paytransaction.PayTransactionEntity;
import com.comerzzia.omnichannel.domain.entity.paytransaction.PayTransactionKey;

public interface PayTransactionMapper {
	PayTransactionEntity selectByPrimaryKey(PayTransactionKey key);
	
    int insert(PayTransactionEntity record);

    int updateTransactionStatus(@Param("activityId") String activityId,
    		@Param("payTransactionUid") String payTransactionUid,
    		@Param("expectedStatusId") Short expectedStatusId,
    		@Param("newStatusId") Short newStatusId,
    		@Param("payTransactionUidLink") String payTransactionUidLink);
}