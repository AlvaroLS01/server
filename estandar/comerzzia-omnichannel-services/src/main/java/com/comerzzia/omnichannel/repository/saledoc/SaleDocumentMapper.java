package com.comerzzia.omnichannel.repository.saledoc;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.comerzzia.omnichannel.domain.dto.saledoc.SaleDocHdrDTO;
import com.comerzzia.omnichannel.domain.entity.saledoc.SaleDocHdrEntity;
import com.comerzzia.omnichannel.domain.entity.saledoc.SaleDocLineEntity;
import com.comerzzia.omnichannel.domain.entity.saledoc.SaleDocLinePriceModEntity;
import com.comerzzia.omnichannel.domain.entity.saledoc.SaleDocPaymentEntity;
import com.comerzzia.omnichannel.domain.entity.saledoc.SaleDocTaxEntity;

public interface SaleDocumentMapper {
	SaleDocHdrEntity selectByDocumentUid(@Param("activityUid") String activityUid, @Param("documentUid") String documentUid);
	SaleDocHdrDTO selectByDocumentUidDTO(@Param("activityUid") String activityUid, @Param("documentUid") String documentUid);

	SaleDocHdrEntity selectByDocumentCode(@Param("activityUid") String activityUid, @Param("documentTypeId") Long documentTypeId, @Param("documentCode") String documentCode);	
	SaleDocHdrDTO selectByDocumentCodeDTO(@Param("activityUid") String activityUid, @Param("documentTypeId") Long documentTypeId, @Param("documentCode") String documentCode);
	
	List<SaleDocLineEntity> selectDocumentLines(@Param("activityUid") String activityUid, @Param("salesDocId") Long salesDocId);
	List<SaleDocTaxEntity> selectDocumentTaxes(@Param("activityUid") String activityUid, @Param("salesDocId") Long salesDocId);
	List<SaleDocPaymentEntity> selectDocumentPayments(@Param("activityUid") String activityUid, @Param("salesDocId") Long salesDocId);
	
	List<SaleDocLinePriceModEntity> selectDocumentLinePriceMods(@Param("activityUid") String activityUid, @Param("salesDocId") Long salesDocId, @Param("lineId") Integer lineId);
	
}