package com.comerzzia.omnichannel.domain.dto.saledoc;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrintDocumentDTO {
	private String mimeType;
    private Boolean copy = false;
    private Boolean screenOutput = false;
    private String printTemplate;
    
    private Boolean inline = false;
    private String outputDocumentName;
    
    private Map<String, Object> customParams;    
    
    public Map<String, Object> getCustomParams() {
    	if (customParams == null) {
    		customParams = new HashMap<String, Object>();    		
    	}    	
    	return customParams;
    }
}