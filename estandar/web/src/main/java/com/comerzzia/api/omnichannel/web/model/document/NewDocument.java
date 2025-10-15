package com.comerzzia.api.omnichannel.web.model.document;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewDocument {
	@NotNull
	@QueryParam(value = "documentUid")
	@javax.validation.constraints.Size(max=40)
	private String documentUid;
	
	@NotNull
	@QueryParam(value = "storeCode")
	@javax.validation.constraints.Size(max=4)	
    private String storeCode;
	
	@NotNull
	@QueryParam(value = "tillCode")
	@javax.validation.constraints.Size(max=4)
    private String tillCode;
    
	@NotNull
    @QueryParam(value = "documentTypeId")
    private Long documentTypeId;
    
    @NotNull
	@QueryParam(value = "documentCode")
	@javax.validation.constraints.Size(max=60)
    private String documentCode;
    
    @NotNull
	@QueryParam(value = "documentSerial")
	@javax.validation.constraints.Size(max=20)
    private String documentSerial;
    
    @NotNull
	@QueryParam(value = "documentNumber")
    private Long documentNumber;
	
    @QueryParam(value = "locatorCode")
	@javax.validation.constraints.Size(max=100)
    @Schema(description = "Unique document locator for every documentTypeId. Default value is documentUid")
    private String locatorCode;

    @NotNull
	@QueryParam(value = "documentContent")
    @Schema(type = "BYTE")
    private byte[] documentContent;

	@QueryParam(value = "documentSignature")
	@javax.validation.constraints.Size(max=255)
    private String documentSignature;
	
	@QueryParam(value = "processed")
	@Schema(defaultValue = "false")
    private Boolean processed = false;

	@QueryParam(value = "processedDate")
    private Date processedDate;
	
	@QueryParam(value = "processedMessage")
	@javax.validation.constraints.Size(max=255)
    private String processedMessage;    
}