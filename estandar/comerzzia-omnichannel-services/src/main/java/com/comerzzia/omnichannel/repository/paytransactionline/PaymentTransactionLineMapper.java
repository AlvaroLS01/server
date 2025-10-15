package com.comerzzia.omnichannel.repository.paytransactionline;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.comerzzia.omnichannel.domain.entity.paytransactionline.PaymentTransactionLineEntity;
import com.comerzzia.omnichannel.domain.entity.paytransactionline.PaymentTransactionLineKey;

public interface PaymentTransactionLineMapper {
    int insert(PaymentTransactionLineEntity record);

    PaymentTransactionLineEntity selectByPrimaryKey(PaymentTransactionLineKey key);

    int countTransactionLines(@Param("activityId") String activityId, @Param("payTransactionUid") String payTransactionUid);
    
    List<PaymentTransactionLineEntity> selectByTransactionUid(@Param("activityId") String activityId, @Param("payTransactionUid") String payTransactionUid);
    
    int updateTransactionLineStatus(@Param("activityId") String activityId,    		
    		@Param("payTransactionLineUid") String payTransactionLineUid,
    		@Param("expectedStatusId") Short expectedStatusId,
    		@Param("newStatusId") Short newStatusId,
    		@Param("endDate") Date endDate,
    		@Param("paymentGatewayResponse") String paymentGatewayResponse);
}