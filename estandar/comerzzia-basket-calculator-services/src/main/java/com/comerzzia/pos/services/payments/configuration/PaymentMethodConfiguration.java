package com.comerzzia.pos.services.payments.configuration;

import java.util.Map;

import com.comerzzia.pos.services.payments.methods.PaymentMethodManager;

public interface PaymentMethodConfiguration {
	
	String getPaymentCode();
	
	String getControlClass();
	
	PaymentMethodManager getManager();
	
	Map<String, String> getConfigurationProperties();
	
	Map<String, String> getGatewayConfigurationProperties();
	
	Map<String, String> getStoreConfigurationProperties();
	
	String getConfigurationProperty(String key);
	
	String getGatewayConfigurationProperty(String key);
	
	String getStoreConfigurationProperty(String key);
	
	void putGatewayConfigurationProperty(String propertyKey, String propertyValue);
	
	void putStoreConfigurationProperty(String propertyKey, String propertyValue);

}
