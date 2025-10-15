package com.comerzzia.omnichannel.service.documentvalidation;

import java.util.Set;

import com.comerzzia.omnichannel.domain.dto.fiscal.FiscalData;
import com.comerzzia.omnichannel.service.documentvalidation.fiscal.ISaleDocumentFiscalProperties;
import com.comerzzia.omnichannel.service.documentvalidation.result.SaleDocumentDataValidationResultDTO;

public interface SaleDocumentDataValidationService<F extends ISaleDocumentProperties> {

    SaleDocumentDataValidationResultDTO validateIssuance(F document);

    SaleDocumentDataValidationResultDTO validatePay(ISaleDocumentProperties document, ISaleDocumentFiscalProperties.Payment payment);

    SaleDocumentDataValidationResultDTO beginIssuance(ISaleDocumentProperties document);

    FiscalData endIssuance(ISaleDocumentProperties document);

    void cancelIssuance(ISaleDocumentProperties document);

    void confirmIssuance(ISaleDocumentProperties document);

    Set<String> getPaymentsMethodAvailables(ISaleDocumentProperties document);

}
