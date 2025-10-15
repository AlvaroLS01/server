package com.comerzzia.omnichannel.repository.document;

import org.apache.ibatis.annotations.Param;

import com.comerzzia.omnichannel.domain.entity.document.DocumentContentEntity;
import com.comerzzia.omnichannel.domain.entity.document.DocumentEntity;

public interface DocumentMapper {
    int insert(DocumentEntity record);
    
    int insertContent(DocumentContentEntity record);

    DocumentEntity selectByDocumentUid(@Param("activityUid") String activityUid, @Param("documentUid") String documentUid);

    DocumentEntity selectByDocumentCode(@Param("activityUid") String activityUid, @Param("documentTypeId") Long documentTypeId, @Param("documentCode") String documentCode);

    DocumentEntity selectByDocumentLocatorCode(@Param("activityUid") String activityUid, @Param("locatorCode") String locatorCode);
    
    DocumentContentEntity selectDocumentContent(@Param("activityUid") String activityUid, @Param("documentUid") String documentUid);
    
    DocumentContentEntity selectDocumentContentType(@Param("activityUid") String activityUid, @Param("documentUid") String documentUid, @Param("contentType") String contentType);
}