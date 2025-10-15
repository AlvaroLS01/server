package com.comerzzia.pos.services.payments.events.listeners;

import java.util.EventListener;

import com.comerzzia.pos.services.payments.events.PaymentSelectEvent;

public interface PaymentSelectListener extends EventListener {
	
	void paymentSelect(PaymentSelectEvent event);

}
