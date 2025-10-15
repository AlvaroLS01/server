package com.comerzzia.api.omnichannel.web.model.document;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Document {
	private String documentUid;
    private String storeCode;
    private String tillCode;
    
    private Long documentTypeId;
    
    private String documentCode;
    private String documentSerial;
    private Long documentNumber;
    private String locatorCode;
    
    private Date creationDate;
    
    @Schema(type = "BYTE")
    private byte[] documentContent;

    private String documentSignature;
    
    private Boolean processed;
    private Date processedDate;
    private String processedMessage;

        
    private Long statusActionId;    
    private Long statusId;    
}