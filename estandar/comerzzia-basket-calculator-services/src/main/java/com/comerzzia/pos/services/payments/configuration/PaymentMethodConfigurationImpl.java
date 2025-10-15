package com.comerzzia.pos.services.payments.configuration;

import java.util.HashMap;
import java.util.Map;

import com.comerzzia.pos.services.ContextHolder;
import com.comerzzia.pos.services.payments.methods.PaymentMethodManager;

public class PaymentMethodConfigurationImpl implements PaymentMethodConfiguration {
	
	protected String paymentCode;
	
	protected String controlClass;
	
	protected Map<String, String> gatewayConfigurationProperties;
	
	protected Map<String, String> storeConfigurationProperties;

	public PaymentMethodConfigurationImpl(String paymentCode, String controlClass) {
		super();
		this.paymentCode = paymentCode;
		this.controlClass = controlClass;
		this.gatewayConfigurationProperties = new HashMap<String, String>();
		this.storeConfigurationProperties = new HashMap<String, String>();
	}

	@Override
	public String getPaymentCode() {
		return paymentCode;
	}

	@Override
	public String getControlClass() {
		return controlClass;
	}

	@Override
	public Map<String, String> getConfigurationProperties() {
		Map<String, String> configurationProperties = new HashMap<String, String>();
		configurationProperties.putAll(gatewayConfigurationProperties);
		configurationProperties.putAll(storeConfigurationProperties);
		return configurationProperties;
	}

	@Override
	public String getConfigurationProperty(String propertyKey) {
		return getConfigurationProperties().get(propertyKey);
	}

	@Override
	public String getGatewayConfigurationProperty(String propertyKey) {
		return gatewayConfigurationProperties.get(propertyKey);
	}

	@Override
	public String getStoreConfigurationProperty(String propertyKey) {
		return storeConfigurationProperties.get(propertyKey);
	}

	@Override
	public void putGatewayConfigurationProperty(String propertyKey, String propertyValue) {
		gatewayConfigurationProperties.put(propertyKey, propertyValue);
	}

	@Override
	public void putStoreConfigurationProperty(String propertyKey, String propertyValue) {
		storeConfigurationProperties.put(propertyKey, propertyValue);
	}

	@Override
	public Map<String, String> getGatewayConfigurationProperties() {
		return gatewayConfigurationProperties;
	}

	@Override
	public Map<String, String> getStoreConfigurationProperties() {
		return storeConfigurationProperties;
	}

	@Override
	public PaymentMethodManager getManager() {
		PaymentMethodManager paymentMethodManager = (PaymentMethodManager) ContextHolder.get().getBean(controlClass);
		if(paymentMethodManager != null) {
			paymentMethodManager.setPaymentCode(paymentCode);
			paymentMethodManager.setConfiguration(this);
		}
		return paymentMethodManager;
	}

}
