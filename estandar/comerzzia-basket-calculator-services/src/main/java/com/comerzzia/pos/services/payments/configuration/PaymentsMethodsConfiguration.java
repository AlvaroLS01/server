package com.comerzzia.pos.services.payments.configuration;

import java.util.List;

import com.comerzzia.pos.services.core.sesion.SesionAplicacion;
import com.comerzzia.pos.services.core.sesion.paymentMethods.PaymentMethodsData;

public interface PaymentsMethodsConfiguration {
	
	List<PaymentMethodConfiguration> getPaymentsMethodsConfiguration();
	
	void loadConfiguration(SesionAplicacion sesion, PaymentMethodsData paymentMethodsData);
	
	void saveConfiguration() throws Exception;

}
