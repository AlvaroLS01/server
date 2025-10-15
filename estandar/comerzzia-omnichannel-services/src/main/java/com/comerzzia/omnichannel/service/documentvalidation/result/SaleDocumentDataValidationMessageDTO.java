package com.comerzzia.omnichannel.service.documentvalidation.result;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDocumentDataValidationMessageDTO {
	public enum Type {INFO, WARN, ERROR};
	
	protected Type messageType;
	
	protected String messageInfo;
	
	protected List<SaleDocumentDataValidationPossibleSolutionDTO> possibleSolutions;
	
	public SaleDocumentDataValidationMessageDTO(Type messageType, String messageInfo) {
	    this.messageType = messageType;
	    this.messageInfo = messageInfo;
	}
}
