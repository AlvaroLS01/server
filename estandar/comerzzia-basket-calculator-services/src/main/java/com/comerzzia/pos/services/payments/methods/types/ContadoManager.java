package com.comerzzia.pos.services.payments.methods.types;

import java.math.BigDecimal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.services.payments.PaymentDto;
import com.comerzzia.pos.services.payments.PaymentException;
import com.comerzzia.pos.services.payments.configuration.PaymentMethodConfiguration;
import com.comerzzia.pos.services.payments.events.PaymentOkEvent;

@Component
@Scope("prototype")
public class ContadoManager extends BasicPaymentMethodManager {

	@Override
	public void setConfiguration(PaymentMethodConfiguration configuration) {
	}

	@Override
	public boolean pay(BigDecimal amount) throws PaymentException {
		PaymentOkEvent event = new PaymentOkEvent(this, paymentId, amount);
		getEventHandler().paymentOk(event);
		return true;
	}

	@Override
	public boolean returnAmount(BigDecimal amount) throws PaymentException {
		PaymentOkEvent event = new PaymentOkEvent(this, paymentId, amount);
		getEventHandler().paymentOk(event);
		return true;
	}

	@Override
	public boolean cancelPay(PaymentDto paymentDto) throws PaymentException {
		PaymentOkEvent event = new PaymentOkEvent(this, paymentDto.getPaymentId(), paymentDto.getAmount());
		event.setCanceled(true);
		getEventHandler().paymentOk(event);
		return true;
	}

	@Override
	public boolean cancelReturn(PaymentDto paymentDto) throws PaymentException {
		PaymentOkEvent event = new PaymentOkEvent(this, paymentDto.getPaymentId(), paymentDto.getAmount());
		event.setCanceled(true);
		getEventHandler().paymentOk(event);
		return true;
	}
	
	@Override
	public boolean recordCashFlowImmediately() {
	    return false;
	}

}
