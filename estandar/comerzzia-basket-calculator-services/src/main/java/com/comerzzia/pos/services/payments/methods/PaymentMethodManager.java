package com.comerzzia.pos.services.payments.methods;

import java.math.BigDecimal;
import java.util.List;

import com.comerzzia.pos.services.payments.PaymentDto;
import com.comerzzia.pos.services.payments.PaymentException;
import com.comerzzia.pos.services.payments.configuration.ConfigurationPropertyDto;
import com.comerzzia.pos.services.payments.configuration.PaymentMethodConfiguration;
import com.comerzzia.pos.services.payments.events.listeners.PaymentErrorListener;
import com.comerzzia.pos.services.payments.events.listeners.PaymentOkListener;
import com.comerzzia.pos.services.payments.events.listeners.PaymentProcessListener;
import com.comerzzia.pos.services.payments.events.listeners.PaymentSelectListener;
import com.comerzzia.pos.services.ticket.ITicket;

@SuppressWarnings("rawtypes")
public interface PaymentMethodManager {
	
	void initialize();
	
	String getPaymentCode();
	
	void setPaymentCode(String paymentCode);
	
	void setConfiguration(PaymentMethodConfiguration configuration);
	
	boolean isAvailable();
	
	boolean isAvailableForReturn();
	
	boolean isAvailableForExchange();
	
	boolean isUniquePayment();
	
	boolean isAsyncExecution();
	
	boolean pay(BigDecimal amount) throws PaymentException;
	
	boolean returnAmount(BigDecimal amount) throws PaymentException;
	
	boolean cancelPay(PaymentDto payment) throws PaymentException;
	
	boolean cancelReturn(PaymentDto payment) throws PaymentException;
	
	boolean select();
	
	void addListenerProcess(PaymentProcessListener listener);
	
	void addListenerOk(PaymentOkListener listener);
	
	void addListenerError(PaymentErrorListener listener);
	
	void addListenerSelect(PaymentSelectListener listener);
	
	void addParameter(String key, Object value);
	
	void setTicketData(ITicket ticket, ITicket ticketOrigen);
	
	List<ConfigurationPropertyDto> getConfigurationProperties();
	
	void setPaymentId(int paymentId);
	
	boolean recordCashFlowImmediately();
	
	BigDecimal getMaxAmount();
	BigDecimal getMaxAmountForeign();

}
