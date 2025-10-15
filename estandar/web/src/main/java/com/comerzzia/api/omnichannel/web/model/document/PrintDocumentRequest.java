package com.comerzzia.api.omnichannel.web.model.document;

import java.util.Map;

import javax.ws.rs.QueryParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrintDocumentRequest {
	@QueryParam(value = "mimeType")
	@Schema(description = "Mime type for output. If null, the request mime type param")
    private String mimeType;
	
	@QueryParam(value = "copy")
	@Schema(defaultValue = "false")
    private Boolean copy = false;
	
	@QueryParam(value = "inline")
	@Schema(defaultValue = "false")
    private Boolean inline = false;
	
	@QueryParam(value = "outputDocumentName")
	@Schema(description = "Default value is documentUid")
    private String outputDocumentName;
	
	@QueryParam(value = "printTemplate")
    @Schema(description = "Optional. If null, document type template or persisted document print format is used")	
	@javax.validation.constraints.Size(max=255)	
    private String printTemplate;
	
	@QueryParam(value = "customParams")
    private Map<String, String> customParams;    
}