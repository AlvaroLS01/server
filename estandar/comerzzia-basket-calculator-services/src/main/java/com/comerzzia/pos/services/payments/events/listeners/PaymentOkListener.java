package com.comerzzia.pos.services.payments.events.listeners;

import com.comerzzia.pos.services.payments.events.PaymentOkEvent;

public interface PaymentOkListener {
	
	void paymentOk(PaymentOkEvent event);

}
