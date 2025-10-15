package com.comerzzia.omnichannel.domain.entity.document;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DocumentEntity {
	private String activityUid;
	private String documentUid;
    private String storeCode;
    private String tillCode;
    
    private Long documentTypeId;
    
    private String documentCode;
    private String documentSerial;
    private Long documentNumber;
    private String locatorCode;
    
    private Date creationDate;
    
    private byte[] documentContent;

    private String documentSignature;
    
    private Boolean processed;
    private Date processedDate;
    private String processedMessage;

        
    private Long statusActionId;    
    private Long statusId;    
}