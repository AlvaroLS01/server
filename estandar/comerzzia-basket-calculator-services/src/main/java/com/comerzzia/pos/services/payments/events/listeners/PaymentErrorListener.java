package com.comerzzia.pos.services.payments.events.listeners;

import com.comerzzia.pos.services.payments.events.PaymentErrorEvent;

public interface PaymentErrorListener {
	
	void paymentError(PaymentErrorEvent event);

}
