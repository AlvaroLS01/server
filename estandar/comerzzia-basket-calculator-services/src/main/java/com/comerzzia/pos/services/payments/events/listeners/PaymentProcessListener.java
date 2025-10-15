package com.comerzzia.pos.services.payments.events.listeners;

import java.util.EventListener;

import com.comerzzia.pos.services.payments.events.PaymentInitEvent;
import com.comerzzia.pos.services.payments.events.PaymentStatusEvent;

public interface PaymentProcessListener extends EventListener {
	
	void paymentInitProcess(PaymentInitEvent event);
	
	void paymentStatus(PaymentStatusEvent event);

}
