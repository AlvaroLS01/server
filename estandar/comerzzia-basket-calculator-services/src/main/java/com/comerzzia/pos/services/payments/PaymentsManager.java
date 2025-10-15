package com.comerzzia.pos.services.payments;

import java.math.BigDecimal;
import java.util.Map;

import com.comerzzia.pos.services.core.sesion.SesionCaja;
import com.comerzzia.pos.services.payments.PaymentsManagerImpl.PaymentsMethodEventHandler;
import com.comerzzia.pos.services.payments.events.listeners.PaymentsCompletedListener;
import com.comerzzia.pos.services.payments.events.listeners.PaymentsErrorListener;
import com.comerzzia.pos.services.payments.events.listeners.PaymentsOkListener;
import com.comerzzia.pos.services.payments.events.listeners.PaymentsProcessListener;
import com.comerzzia.pos.services.payments.events.listeners.PaymentsSelectListener;
import com.comerzzia.pos.services.payments.methods.PaymentMethodManager;
import com.comerzzia.pos.services.ticket.ITicket;

@SuppressWarnings("rawtypes")
public interface PaymentsManager {
	
	void updatePaymentsMethodsConfiguration();
	
	void setTicketData(ITicket ticket, ITicket ticketOrigen);
	
	boolean isPaymentMethodAvailable(String paymentCode);
	
	boolean isPaymentMethodAvailableForReturn(String paymentCode);
	
	boolean isExchangePaymentMethodAvailable(String paymentCode);
	
	void pay(String paymentCode, BigDecimal amount);
	
	void returnAmount(String paymentCode, BigDecimal amount);
	
	void cancelPay(int paymentId);
	
	void cancelReturn(int paymentId);
	
	void select(String paymentCode);
	
	void addListenerProcess(PaymentsProcessListener listener);
	
	void addListenerOk(PaymentsOkListener listener);
	
	void addListenerError(PaymentsErrorListener listener);
	
	void addListenerPaymentCompleted(PaymentsCompletedListener listener);
	
	void addListenerPaymentSelect(PaymentsSelectListener listener);
	
	Map<Integer, PaymentDto> getCurrentPayments();
	
	Map<String, PaymentMethodManager> getPaymentsMehtodManagerAvailables();
	
	PaymentsMethodEventHandler getEventsHandler();

	void setSesionCaja(SesionCaja sesionCaja);

}
