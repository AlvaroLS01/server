package com.comerzzia.omnichannel.service.documentvalidation;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.comerzzia.core.servicios.ContextHolder;

import lombok.extern.log4j.Log4j;

@Log4j
public class SaleDocumentValidatorFactory {
	public static final String SERVICE_NAME = "saleDocumentDataValidationService";
	public static final String FISCAL_SERVICE_NAME = "fiscalDataValidationService";
	public static final String OMS_SERVICE_NAME = "omsServiceDataValidationService";
	
	protected static Map<String, Object> servicesMap = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	protected static <T> T getService(String fullServiceName, String activityUid, String countryCode, String docTypeCode) {
		String key = activityUid + "_" + fullServiceName + "_" + countryCode + "_" + docTypeCode;
		
		T result = (T)servicesMap.get(key);
		
		if (result == null) {
			try {
				result = (T) ContextHolder.get().getBean(fullServiceName, countryCode, docTypeCode);
				
				servicesMap.put(key, result);
			} catch (NoSuchBeanDefinitionException e) {
				return null;
			}
		}
		
		return result;
	}
		
	public static <T> T getServiceFor(String serviceName, String activityUid, String countryCode, String docTypeCode) {
		T result = getServiceFor(serviceName + "_" + countryCode + "_" + docTypeCode, activityUid, countryCode, docTypeCode);
		
		if (result == null) {
			result = getService(serviceName + "_" + countryCode, activityUid, countryCode, docTypeCode);
		}
		
		if (result == null) {
			result = getService(serviceName + "_" + docTypeCode, activityUid, countryCode, docTypeCode);
		}		
		
		if (result == null) {
			result = getService("default" + StringUtils.capitalize(serviceName), activityUid, countryCode, docTypeCode);
			
			if (result == null) {
				throw new RuntimeException("Default sale document validator not found: " + serviceName);
			}
		}
		
		log.debug("Using validator " + result.getClass().getCanonicalName());

		
		return result;
	}
		
	public static SaleDocumentDataValidationService<?> getSaleDocumentValidator(String activityUid, String countryCode, String docTypeCode) {
		return getServiceFor(SERVICE_NAME, activityUid, countryCode, docTypeCode);
	}
	
}
