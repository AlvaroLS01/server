package com.comerzzia.pos.services.payments;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.persistence.cajas.movimientos.CashJournalLine;
import com.comerzzia.pos.services.cajas.CajasService;
import com.comerzzia.pos.services.cajas.CajasServiceException;
import com.comerzzia.pos.services.core.sesion.SesionCaja;
import com.comerzzia.pos.services.payments.configuration.PaymentMethodConfiguration;
import com.comerzzia.pos.services.payments.events.PaymentErrorEvent;
import com.comerzzia.pos.services.payments.events.PaymentInitEvent;
import com.comerzzia.pos.services.payments.events.PaymentOkEvent;
import com.comerzzia.pos.services.payments.events.PaymentSelectEvent;
import com.comerzzia.pos.services.payments.events.PaymentStatusEvent;
import com.comerzzia.pos.services.payments.events.PaymentsCompletedEvent;
import com.comerzzia.pos.services.payments.events.PaymentsErrorEvent;
import com.comerzzia.pos.services.payments.events.PaymentsInitEvent;
import com.comerzzia.pos.services.payments.events.PaymentsOkEvent;
import com.comerzzia.pos.services.payments.events.PaymentsSelectEvent;
import com.comerzzia.pos.services.payments.events.PaymentsStatusEvent;
import com.comerzzia.pos.services.payments.events.listeners.PaymentErrorListener;
import com.comerzzia.pos.services.payments.events.listeners.PaymentOkListener;
import com.comerzzia.pos.services.payments.events.listeners.PaymentProcessListener;
import com.comerzzia.pos.services.payments.events.listeners.PaymentSelectListener;
import com.comerzzia.pos.services.payments.events.listeners.PaymentsCompletedListener;
import com.comerzzia.pos.services.payments.events.listeners.PaymentsErrorListener;
import com.comerzzia.pos.services.payments.events.listeners.PaymentsOkListener;
import com.comerzzia.pos.services.payments.events.listeners.PaymentsProcessListener;
import com.comerzzia.pos.services.payments.events.listeners.PaymentsSelectListener;
import com.comerzzia.pos.services.payments.methods.PaymentMethodManager;
import com.comerzzia.pos.services.payments.methods.types.BasicPaymentMethodManager;
import com.comerzzia.pos.services.ticket.ITicket;
import com.comerzzia.pos.services.ticket.Ticket;
import com.comerzzia.pos.services.ticket.TicketsService;
import com.comerzzia.pos.services.ticket.TicketsServiceException;
import com.comerzzia.pos.services.ticket.pagos.IPagoTicket;
import com.comerzzia.pos.services.ticket.pagos.PagoTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.i18n.I18N;

@SuppressWarnings({"rawtypes", "unchecked"})
@Component
@Scope("prototype")
public class PaymentsManagerImpl implements PaymentsManager {

	protected Logger log = Logger.getLogger(getClass());
	
	@Autowired
	protected TicketsService ticketsService;
	
	@Autowired
	protected CajasService cajasService;
	
	protected ITicket ticket;

	protected ITicket ticketOrigen;

	protected boolean paymentCompleted;

	protected Map<String, PaymentMethodManager> paymentsAvailable;

	protected List<PaymentsOkListener> listenerOk;

	protected List<PaymentsErrorListener> listenerError;

	protected List<PaymentsProcessListener> listenerProcess;

	protected List<PaymentsCompletedListener> listenerCompleted;

	protected List<PaymentsSelectListener> listenerSelect;

	protected BigDecimal pending;

	protected PaymentsMethodEventHandler eventsHandler;
	
	protected Map<Integer, PaymentDto> currentPayments;

	protected int paymentId;
	
	protected SesionCaja sesionCaja;

	public PaymentsManagerImpl() {
		this.paymentsAvailable = new HashMap<String, PaymentMethodManager>();
		this.eventsHandler = new PaymentsMethodEventHandler();

		this.listenerCompleted = new ArrayList<PaymentsCompletedListener>();
		this.listenerOk = new ArrayList<PaymentsOkListener>();
		this.listenerError = new ArrayList<PaymentsErrorListener>();
		this.listenerProcess = new ArrayList<PaymentsProcessListener>();
		this.listenerSelect = new ArrayList<PaymentsSelectListener>();
		
		this.currentPayments = new HashMap<Integer, PaymentDto>();
		this.paymentId = 0;
	}
	
	@Override
	public void setSesionCaja(SesionCaja sesionCaja) {
		this.sesionCaja = sesionCaja;
		
		updatePaymentsMethodsConfiguration();
	}
	
	@Override
	public void updatePaymentsMethodsConfiguration() {		
		paymentsAvailable.clear();
		
		for (PaymentMethodConfiguration paymentMethodConfiguration : sesionCaja.getPaymentMethodsData().getPaymentMethodConfigurations()) {
			try {
				String controlClass = paymentMethodConfiguration.getControlClass();
				if (StringUtils.isNotBlank(controlClass)) {
					PaymentMethodManager paymentMethodManager = paymentMethodConfiguration.getManager();

					registerListenerPaymentMethodManager(paymentMethodManager);

					paymentsAvailable.put(paymentMethodConfiguration.getPaymentCode(), paymentMethodManager);
				}
				else {
					log.error("setPaymentsMethodsConfiguration() - El medio de pago " + paymentMethodConfiguration.getPaymentCode()
					        + " no tiene definida clase de control. No saldrá entre los medios de pagos disponibles.");
				}
			}
			catch (Exception e) {
				if(e instanceof NoSuchBeanDefinitionException) {
					log.error("setPaymentsMethodsConfiguration() - There an error at loading payment method manager: " + e.getMessage());
				}
				else {
					log.error("setPaymentsMethodsConfiguration() - There an error at loading payment methods " + e.getMessage(), e);
				}
			}
		}
	}

	protected void registerListenerPaymentMethodManager(PaymentMethodManager paymentMethodManager) {
		paymentMethodManager.addListenerOk(new PaymentOkListener(){
			@Override
			public void paymentOk(PaymentOkEvent event) {
				fireListenerPaymentOk(event);
			}
		});

		paymentMethodManager.addListenerError(new PaymentErrorListener(){
			@Override
			public void paymentError(PaymentErrorEvent event) {
				fireListenerPaymentError(event);
			}
		});

		paymentMethodManager.addListenerProcess(new PaymentProcessListener(){
			@Override
			public void paymentStatus(PaymentStatusEvent event) {
				fireListenerPaymentStatus(event);
			}

			@Override
			public void paymentInitProcess(PaymentInitEvent event) {
				fireListenerPaymentInit(event);
			}
		});
		
		paymentMethodManager.addListenerSelect(new PaymentSelectListener(){
			@Override
			public void paymentSelect(PaymentSelectEvent event) {
				fireListenerPaymentSelect(event);
			}
		});
	}

	protected void fireListenerPaymentOk(PaymentOkEvent event) {
		PaymentsOkEvent eventOk = new PaymentsOkEvent(this, event);
		for (PaymentsOkListener listener : listenerOk) {
			listener.paymentsOk(eventOk);
		}
		
		if(!event.isCanceled()) {
			addCurrentPayment(event);
		}
	}

	protected void fireListenerPaymentError(PaymentErrorEvent event) {
		PaymentsErrorEvent eventError = new PaymentsErrorEvent(this, event);
		for (PaymentsErrorListener listener : listenerError) {
			listener.paymentsError(eventError);
		}
	}

	protected void fireListenerPaymentStatus(PaymentStatusEvent event) {
		PaymentsStatusEvent eventStatus = new PaymentsStatusEvent(this, event);
		for (PaymentsProcessListener listener : listenerProcess) {
			listener.paymentStatus(eventStatus);
		}
	}

	protected void fireListenerPaymentInit(PaymentInitEvent event) {
		PaymentsInitEvent eventInit = new PaymentsInitEvent(this, event);
		for (PaymentsProcessListener listener : listenerProcess) {
			listener.paymentInitProcess(eventInit);
		}
	}

	protected void fireListenerPaymentSelect(PaymentSelectEvent event) {
		PaymentsSelectEvent eventSelect = new PaymentsSelectEvent(this, event);
		for (PaymentsSelectListener listener : listenerSelect) {
			listener.paymentsSelect(eventSelect);
		}
	}

	@Override
	public void setTicketData(ITicket ticket, ITicket ticketOrigen) {
		this.ticket = ticket;
		this.ticketOrigen = ticketOrigen;
		this.paymentCompleted = false;
		this.pending = ticket.getCabecera().getTotales().getPendiente();
		
		setPending(ticket);

		initializePaymentMethodsManager(ticket, ticketOrigen);
		
		addExistingPayments(ticket);
		
		setMaxIdPayment(ticket.getPagos());
	}
	
	protected void setMaxIdPayment(List<PagoTicket> pagos) {
		for(PagoTicket pago : pagos) {
			if(pago.getPaymentId() != null && pago.getPaymentId() >= this.paymentId) {
				this.paymentId = pago.getPaymentId() + 1;
			}
		}
    }

	protected void addExistingPayments(ITicket ticket) {
		for(IPagoTicket pago : (List<IPagoTicket>) ticket.getPagos()) {
			if(pago.getPaymentId() != null) { 
				PaymentDto paymentDto = new PaymentDto();
				paymentDto.setPaymentId(pago.getPaymentId());
				paymentDto.setPaymentCode(pago.getCodMedioPago());
				paymentDto.setAmount(pago.getImporte());
				Map<String, Object> extendedData = pago.getExtendedData();
				if(pago.getDatosRespuestaPagoTarjeta() != null) {
					extendedData.put(BasicPaymentMethodManager.PARAM_RESPONSE_TEF, pago.getDatosRespuestaPagoTarjeta());
				}
				paymentDto.setExtendedData(extendedData);
				currentPayments.put(pago.getPaymentId(), paymentDto);
			}
		}
	}

	protected void setPending(ITicket ticket) {
		if(BigDecimalUtil.isMenorACero(ticket.getCabecera().getTotales().getTotalAPagar())) {
			pending = pending.abs().negate();
		}
	}

	protected void initializePaymentMethodsManager(ITicket ticket, ITicket ticketOrigen) {
		for (PaymentMethodManager paymentMethodManager : paymentsAvailable.values()) {
			paymentMethodManager.initialize();
			paymentMethodManager.setTicketData(ticket, ticketOrigen);
		}
	}

	@Override
	public boolean isPaymentMethodAvailable(String paymentCode) {
		return paymentsAvailable.containsKey(paymentCode);
	}
	
	@Override
	public boolean isExchangePaymentMethodAvailable(String paymentCode) {
		return paymentsAvailable.containsKey(paymentCode) && paymentsAvailable.get(paymentCode).isAvailableForExchange();
	}

	@Override
	public boolean isPaymentMethodAvailableForReturn(String paymentCode) {
		PaymentMethodManager paymentMethodManager = paymentsAvailable.get(paymentCode);
		if(paymentMethodManager != null) {
			return paymentMethodManager.isAvailableForReturn();
		}
		else {
			return false;
		}
	}

	@Override
	public void pay(String paymentCode, BigDecimal amount) {
		try {
			log.debug("pay() - Se va a pagar " + amount + " del medio de pago " + paymentCode);
			
			PaymentMethodManager manager = paymentsAvailable.get(paymentCode);
			
			if(manager.isUniquePayment() && existsPayment(paymentCode)) {
				PaymentErrorEvent errorEvent = new PaymentErrorEvent(this, -1, null, null, I18N.getTexto("Este medio de pago no permite más de un pago."));
				PaymentsErrorEvent event = new PaymentsErrorEvent(this, errorEvent);
				getEventsHandler().paymentsError(event);
				return;
			}
			
			if(manager.recordCashFlowImmediately()) {
				assignIdTicket();
			}
			
			manager.setPaymentId(paymentId);
			boolean paymentOk = manager.pay(amount);
			incrementPaymentId();
			
			if(paymentOk) {
				if(manager.recordCashFlowImmediately()) {
					recordCashFlow(paymentCode, amount, true);
				}
				
				pending = pending.subtract(amount);
				log.debug("pay() - Importe pendiente: " + pending);
				
				if (BigDecimalUtil.isMenorOrIgualACero(pending)) {
					PaymentsCompletedEvent event = new PaymentsCompletedEvent(this);
					getEventsHandler().paymentsCompleted(event);
				}
			}
		}
		catch(Exception e) {
			log.error("pay() - Ha habido un error al realizar el pago: " + e.getMessage(), e);
			
			PaymentErrorEvent errorEvent = new PaymentErrorEvent(this, -1, e, null, null);
			PaymentsErrorEvent event = new PaymentsErrorEvent(this, errorEvent);
			getEventsHandler().paymentsError(event);
		}
	}

	@Override
	public void returnAmount(String paymentCode, BigDecimal amount) {
		try {
			log.debug("returnAmount() - Se va a devolver " + amount + " del medio de pago " + paymentCode);
			
			PaymentMethodManager manager = paymentsAvailable.get(paymentCode);

			if(manager.recordCashFlowImmediately()) {
				assignIdTicket();
			}
	        
			manager.setPaymentId(paymentId);
			boolean returnOk = manager.returnAmount(amount);
			incrementPaymentId();

			if(returnOk) {
				if(manager.recordCashFlowImmediately()) {
					recordCashFlow(paymentCode, amount, false);
				}
				
				pending = pending.add(amount);
				log.debug("returnAmount() - Importe pendiente: " + pending);
				
				if (BigDecimalUtil.isMayorOrIgualACero(pending)) {
					PaymentsCompletedEvent event = new PaymentsCompletedEvent(this);
					getEventsHandler().paymentsCompleted(event);
				}
			}
		}
		catch(Exception e) {
			log.error("returnAmount() - Ha habido un error al realizar la devolución: " + e.getMessage(), e);
			
			PaymentErrorEvent errorEvent = new PaymentErrorEvent(this, -1, e, null, null);
			PaymentsErrorEvent event = new PaymentsErrorEvent(this, errorEvent);
			getEventsHandler().paymentsError(event);
		}
	}

	protected void assignIdTicket() throws TicketsServiceException {
		if(ticket.getCabecera().getIdTicket() == null) {
			log.debug("assignIdTicket() - Se va a asignar el ID_TICKET ya que el ticket no tiene pagos.");
		    ticketsService.setContadorIdTicket((Ticket) ticket);
		    log.debug("assignIdTicket() - Contador obtenido: " + ticket.getIdTicket());
		}
	}

	@Override
	public void cancelPay(int paymentId) {
		try {
			PaymentDto paymentDto = currentPayments.get(paymentId);
			
			if(paymentDto != null) {
				String paymentCode = paymentDto.getPaymentCode();
				
				PaymentMethodManager manager = paymentsAvailable.get(paymentCode);
				boolean cancelOk = manager.cancelPay(paymentDto);
				
				if(cancelOk) {
					PaymentDto payment = currentPayments.get(paymentId);
					BigDecimal amount = payment.getAmount();
					
					if(manager.recordCashFlowImmediately()) {
						recordCashFlow(paymentCode, amount, false);
					}
					
					pending = pending.add(amount);
					log.debug("cancelPay() - Importe pendiente: " + pending);
					
					currentPayments.remove(paymentId);
				}
			}
		}
		catch(Exception e) {
			log.error("cancelPay() - Ha habido un error al cancelar el pago: " + e.getMessage(), e);
			
			PaymentErrorEvent errorEvent = new PaymentErrorEvent(this, paymentId, e, null, null);
			PaymentsErrorEvent event = new PaymentsErrorEvent(this, errorEvent);
			getEventsHandler().paymentsError(event);
		}
	}

	@Override
	public void cancelReturn(int paymentId) {
		try {
			PaymentDto paymentDto = currentPayments.get(paymentId);
			
			if(paymentDto != null) {
				String paymentCode = paymentDto.getPaymentCode();
				
				PaymentMethodManager manager = paymentsAvailable.get(paymentCode);
				boolean returnOk = manager.cancelReturn(paymentDto);
				
				if(returnOk) {
					PaymentDto payment = currentPayments.get(paymentId);
					BigDecimal amount = payment.getAmount();
					
					if(manager.recordCashFlowImmediately()) {
						recordCashFlow(paymentCode, amount, true);
					}
					
					pending = pending.subtract(amount);
					log.debug("cancelReturn() - Importe pendiente: " + pending);
					
					currentPayments.remove(paymentId);
				}
			}
		}
		catch(Exception e) {
			log.error("cancelReturn() - Ha habido un error al cancelar la devolución: " + e.getMessage(), e);
			
			PaymentErrorEvent errorEvent = new PaymentErrorEvent(this, paymentId, e, null, null);
			PaymentsErrorEvent event = new PaymentsErrorEvent(this, errorEvent);
			getEventsHandler().paymentsError(event);
		}
	}

	protected void addCurrentPayment(PaymentOkEvent event) {
		PaymentDto paymentDto = new PaymentDto();
		paymentDto.setPaymentId(event.getPaymentId());
		paymentDto.setPaymentCode(((PaymentMethodManager) event.getSource()).getPaymentCode());
		paymentDto.setAmount(event.getAmount());
		paymentDto.setExtendedData(event.getExtendedData());
		currentPayments.put(event.getPaymentId(), paymentDto);
	}

	@Override
	public void select(String paymentCode) {
		PaymentMethodManager manager = paymentsAvailable.get(paymentCode);
		manager.select();
	}

	@Override
	public void addListenerProcess(PaymentsProcessListener listener) {
		listenerProcess.add(listener);
	}

	@Override
	public void addListenerOk(PaymentsOkListener listener) {
		listenerOk.add(listener);
	}

	@Override
	public void addListenerError(PaymentsErrorListener listener) {
		listenerError.add(listener);
	}

	@Override
	public void addListenerPaymentCompleted(PaymentsCompletedListener listener) {
		listenerCompleted.add(listener);
	}

	@Override
	public void addListenerPaymentSelect(PaymentsSelectListener listener) {
		listenerSelect.add(listener);
	}

	public class PaymentsMethodEventHandler implements PaymentsOkListener, PaymentsErrorListener, PaymentsProcessListener, PaymentsCompletedListener {

		@Override
		public void paymentInitProcess(PaymentsInitEvent event) {
			firePaymentsInitProcessEvent(event);
		}

		@Override
		public void paymentsCompleted(PaymentsCompletedEvent event) {
			firePaymentsCompletedEvent(event);
		}

		@Override
		public void paymentStatus(PaymentsStatusEvent event) {
			firePaymentsStatusEvent(event);
		}

		@Override
		public void paymentsError(PaymentsErrorEvent event) {
			firePaymentsErrorProcessEvent(event);
		}

		@Override
		public void paymentsOk(PaymentsOkEvent event) {
			firePaymentsOkProcessEvent(event);
		}

	}

	protected void firePaymentsCompletedEvent(PaymentsCompletedEvent event) {
		List<PaymentsCompletedListener> listeners = this.listenerCompleted;
		for (PaymentsCompletedListener listener : listeners) {
			listener.paymentsCompleted(event);
		}
	}

	public void firePaymentsErrorProcessEvent(PaymentsErrorEvent event) {
		List<PaymentsErrorListener> listeners = this.listenerError;
		for (PaymentsErrorListener listener : listeners) {
			listener.paymentsError(event);
		}
	}

	public void firePaymentsOkProcessEvent(PaymentsOkEvent event) {
		List<PaymentsOkListener> listeners = this.listenerOk;
		for (PaymentsOkListener listener : listeners) {
			listener.paymentsOk(event);
		}
	}

	public void firePaymentsStatusEvent(PaymentsStatusEvent event) {
		List<PaymentsProcessListener> listeners = this.listenerProcess;
		for (PaymentsProcessListener listener : listeners) {
			listener.paymentStatus(event);
		}
	}

	public void firePaymentsInitProcessEvent(PaymentsInitEvent event) {
		List<PaymentsProcessListener> listeners = this.listenerProcess;
		for (PaymentsProcessListener listener : listeners) {
			listener.paymentInitProcess(event);
		}
	}

	public PaymentsMethodEventHandler getEventsHandler() {
		return eventsHandler;
	}

	@Override
	public Map<Integer, PaymentDto> getCurrentPayments() {
		return currentPayments;
	}

	protected void incrementPaymentId() {
		this.paymentId++;
    }

	@Override
    public Map<String, PaymentMethodManager> getPaymentsMehtodManagerAvailables() {
	    return paymentsAvailable;
    }
	
	protected boolean existsPayment(String paymentCode) {
		for(PaymentDto paymentDto : currentPayments.values()) {
			if(paymentDto.getPaymentCode().equals(paymentCode)) {
				return true;
			}
		}
		return false;
	}

	protected void recordCashFlow(String paymentCode, BigDecimal amount, boolean isIncome) throws CajasServiceException {
		log.debug("recordCashFlow() - Se va a guardar un movimiento de caja: [medio de pago: " + paymentCode + ", importe: " + amount + ", signo: " + (isIncome ? "+" : "-") + "]");
		
		CashJournalLine movimiento = new CashJournalLine();
		movimiento.setCashJournalDate(new Date());
	    
	    if(isIncome){
	    	movimiento.setOutput(amount);
	    	movimiento.setInput(BigDecimal.ZERO);     	
	    }
	    else{
	    	movimiento.setOutput(BigDecimal.ZERO);
	    	movimiento.setInput(amount);
	    }
	    
	    movimiento.setConcept(ticket.getCabecera().getDesTipoDocumento() + ": " + ticket.getCabecera().getCodTicket());
	    movimiento.setSalesDocCode(ticket.getCabecera().getCodTicket());
	    movimiento.setPaymentMethodCode(paymentCode);
	    movimiento.setSalesDocUid(ticket.getUidTicket());
	    movimiento.setDocTypeId(ticket.getCabecera().getTipoDocumento());
	    
	    cajasService.crearMovimiento(movimiento);
	}

}
