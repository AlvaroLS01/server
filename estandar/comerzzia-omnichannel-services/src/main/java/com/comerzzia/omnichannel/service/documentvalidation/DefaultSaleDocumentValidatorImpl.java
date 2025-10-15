package com.comerzzia.omnichannel.service.documentvalidation;

import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.omnichannel.domain.dto.fiscal.FiscalData;
import com.comerzzia.omnichannel.service.documentvalidation.fiscal.FiscalDataValidationService;
import com.comerzzia.omnichannel.service.documentvalidation.fiscal.ISaleDocumentFiscalProperties;
import com.comerzzia.omnichannel.service.documentvalidation.oms.OmsServiceDataValidationService;
import com.comerzzia.omnichannel.service.documentvalidation.result.SaleDocumentDataValidationResultDTO;

@Component("defaultSaleDocumentValidatorService")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DefaultSaleDocumentValidatorImpl implements SaleDocumentDataValidationService<ISaleDocumentProperties> {
    protected String activityUid;
	protected String countryCode;
	protected String docTypeCode;		
	protected FiscalDataValidationService<ISaleDocumentFiscalProperties> fiscalDataService;
	protected OmsServiceDataValidationService omsServiceDataValidationService;
		
	public DefaultSaleDocumentValidatorImpl(String activityUid, String countryCode, String docTypeCode) {
	    this.activityUid = activityUid;
		this.countryCode = countryCode;
		this.docTypeCode = docTypeCode;
		this.fiscalDataService = SaleDocumentValidatorFactory.getServiceFor(SaleDocumentValidatorFactory.FISCAL_SERVICE_NAME, activityUid, countryCode, docTypeCode);
		this.omsServiceDataValidationService = SaleDocumentValidatorFactory.getServiceFor(SaleDocumentValidatorFactory.OMS_SERVICE_NAME, activityUid, countryCode, docTypeCode);
	}

    @Override
    public SaleDocumentDataValidationResultDTO validateIssuance(ISaleDocumentProperties document) {
        SaleDocumentDataValidationResultDTO result = new SaleDocumentDataValidationResultDTO();
        
        result.getValidationMessages().addAll(fiscalDataService.validateIssuance(document).getValidationMessages());
        
        return result;
    }

    @Override
    public SaleDocumentDataValidationResultDTO validatePay(ISaleDocumentProperties document, ISaleDocumentFiscalProperties.Payment payment) {
        return fiscalDataService.validatePay(document, payment);
    }

    @Override
    public SaleDocumentDataValidationResultDTO beginIssuance(ISaleDocumentProperties document) {
        SaleDocumentDataValidationResultDTO result = new SaleDocumentDataValidationResultDTO();
        
        result.getValidationMessages().addAll(fiscalDataService.beginIssuance(document).getValidationMessages());
        
        return result;
    }

    @Override
    public FiscalData endIssuance(ISaleDocumentProperties document) {
        return fiscalDataService.endIssuance(document);
    }

    @Override
    public void cancelIssuance(ISaleDocumentProperties document) {
        fiscalDataService.cancelIssuance(document);        
    }

    @Override
    public void confirmIssuance(ISaleDocumentProperties document) {
        fiscalDataService.confirmIssuance(document);        
    }

    @Override
    public Set<String> getPaymentsMethodAvailables(ISaleDocumentProperties document) {
        return fiscalDataService.getPaymentsMethodAvailables(document);
    }

}
