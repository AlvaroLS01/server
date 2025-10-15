package com.comerzzia.pos.services.payments.events.listeners;

import com.comerzzia.pos.services.payments.events.PaymentsOkEvent;

public interface PaymentsOkListener {
	
	void paymentsOk(PaymentsOkEvent event);

}
