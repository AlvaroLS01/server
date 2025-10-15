package com.comerzzia.pos.services.payments.events.listeners;

import java.util.EventListener;

import com.comerzzia.pos.services.payments.events.PaymentsInitEvent;
import com.comerzzia.pos.services.payments.events.PaymentsStatusEvent;

public interface PaymentsProcessListener extends EventListener {
	
	void paymentInitProcess(PaymentsInitEvent event);
	
	void paymentStatus(PaymentsStatusEvent event);

}
