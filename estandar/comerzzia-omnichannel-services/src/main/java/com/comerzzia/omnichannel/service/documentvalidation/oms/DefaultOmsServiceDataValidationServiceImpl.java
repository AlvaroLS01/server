package com.comerzzia.omnichannel.service.documentvalidation.oms;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("defaultOmsServiceDataValidationService")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DefaultOmsServiceDataValidationServiceImpl implements OmsServiceDataValidationService {
    protected String activityUid;
	protected String countryCode;
	protected String docTypeCode;
	
	public DefaultOmsServiceDataValidationServiceImpl(String activityUid, String countryCode, String docTypeCode) {
	    this.activityUid = activityUid;
		this.countryCode = countryCode;
		this.docTypeCode = docTypeCode;
	}
}
