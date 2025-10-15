package com.comerzzia.omnichannel.service.documentvalidation.fiscal;

import java.util.Set;

import com.comerzzia.omnichannel.domain.dto.fiscal.FiscalData;
import com.comerzzia.omnichannel.service.documentvalidation.result.SaleDocumentDataValidationResultDTO;

public interface FiscalDataValidationService<T extends ISaleDocumentFiscalProperties> {
	
	SaleDocumentDataValidationResultDTO validateIssuance(final T document);
	
	SaleDocumentDataValidationResultDTO validatePay(final T document, T.Payment payment);
	
	SaleDocumentDataValidationResultDTO beginIssuance(final T document);
	
	FiscalData endIssuance(final T document);
	
	void cancelIssuance(final T document);
	
	void confirmIssuance(final T document);
	
	Set<String> getPaymentsMethodAvailables(final T document);
	
}
