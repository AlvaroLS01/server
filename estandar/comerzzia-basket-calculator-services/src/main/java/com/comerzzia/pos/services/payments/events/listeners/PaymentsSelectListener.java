package com.comerzzia.pos.services.payments.events.listeners;

import java.util.EventListener;

import com.comerzzia.pos.services.payments.events.PaymentsSelectEvent;

public interface PaymentsSelectListener extends EventListener {
	
	void paymentsSelect(PaymentsSelectEvent event);

}
