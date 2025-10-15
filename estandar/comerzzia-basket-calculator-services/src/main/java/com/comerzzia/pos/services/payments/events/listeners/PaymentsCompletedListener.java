package com.comerzzia.pos.services.payments.events.listeners;

import com.comerzzia.pos.services.payments.events.PaymentsCompletedEvent;

public interface PaymentsCompletedListener {
	
	void paymentsCompleted(PaymentsCompletedEvent event);

}
