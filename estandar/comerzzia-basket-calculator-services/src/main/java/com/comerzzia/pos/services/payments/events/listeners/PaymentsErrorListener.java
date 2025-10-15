package com.comerzzia.pos.services.payments.events.listeners;

import com.comerzzia.pos.services.payments.events.PaymentsErrorEvent;

public interface PaymentsErrorListener {
	
	void paymentsError(PaymentsErrorEvent event);

}
