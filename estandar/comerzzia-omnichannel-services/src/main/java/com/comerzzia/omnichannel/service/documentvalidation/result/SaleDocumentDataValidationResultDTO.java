package com.comerzzia.omnichannel.service.documentvalidation.result;

import java.util.ArrayList;
import java.util.List;

public class SaleDocumentDataValidationResultDTO {	
	protected List<SaleDocumentDataValidationMessageDTO> validationMessages = new ArrayList<>();

	public boolean isValid() {
		for(SaleDocumentDataValidationMessageDTO message : validationMessages) {
			if(SaleDocumentDataValidationMessageDTO.Type.ERROR.equals(message.getMessageType())) {
				return false;
			}
		}
		return true;
	}

	public boolean isConfirmRequired() {
		for(SaleDocumentDataValidationMessageDTO message : validationMessages) {
			if(SaleDocumentDataValidationMessageDTO.Type.WARN.equals(message.getMessageType())) {
				return true;
			}
		}
		return false;
	}

	public List<SaleDocumentDataValidationMessageDTO> getValidationMessages() {
		return validationMessages;
	}

	public void setValidationMessages(List<SaleDocumentDataValidationMessageDTO> validationMessages) {
		this.validationMessages = validationMessages;
	}
	
	public void addError(String message) {
	    add(SaleDocumentDataValidationMessageDTO.Type.ERROR, message);
	}
	
	public void addInfo(String message) {
        add(SaleDocumentDataValidationMessageDTO.Type.INFO, message);
    }
	
	public void addWarn(String message) {
        add(SaleDocumentDataValidationMessageDTO.Type.WARN, message);
    }
	
	public void add(SaleDocumentDataValidationMessageDTO.Type type, String message) {
	    validationMessages.add(new SaleDocumentDataValidationMessageDTO(type, message));
	}

}
