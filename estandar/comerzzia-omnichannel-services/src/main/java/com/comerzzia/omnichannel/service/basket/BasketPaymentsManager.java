package com.comerzzia.omnichannel.service.basket;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.omnichannel.domain.dto.basket.EndTransactionDTO;
import com.comerzzia.omnichannel.domain.dto.basket.pay.PaymentsStatusDto;
import com.comerzzia.omnichannel.domain.dto.basket.pay.TenderAcceptedDTO;
import com.comerzzia.omnichannel.domain.dto.basket.pay.TenderDTO;
import com.comerzzia.omnichannel.domain.dto.basket.pay.TenderExceptionDTO;
import com.comerzzia.omnichannel.domain.entity.paytransaction.PayTransactionEntity;
import com.comerzzia.omnichannel.domain.entity.paytransactionline.PaymentTransactionLineEntity;
import com.comerzzia.omnichannel.service.paytransaction.PayTransactionService;
import com.comerzzia.pos.services.payments.PaymentException;
import com.comerzzia.pos.services.payments.PaymentsManager;
import com.comerzzia.pos.services.payments.events.PaymentErrorEvent;
import com.comerzzia.pos.services.payments.events.PaymentOkEvent;
import com.comerzzia.pos.services.payments.events.PaymentsCompletedEvent;
import com.comerzzia.pos.services.payments.events.PaymentsErrorEvent;
import com.comerzzia.pos.services.payments.events.PaymentsOkEvent;
import com.comerzzia.pos.services.payments.events.listeners.PaymentsCompletedListener;
import com.comerzzia.pos.services.payments.events.listeners.PaymentsErrorListener;
import com.comerzzia.pos.services.payments.events.listeners.PaymentsOkListener;
import com.comerzzia.pos.services.payments.methods.PaymentMethodManager;

//TODO: Ojo transacciones de bbdd entre la cesta y el detalle de sus transacciones de pago
//TODO: Devoluciones, cancelación de pagos
//TODO: Confirmación de transacciones pre-autorizadas
//TODO: Ojo devoluciones en vales/tarjetas regalo
//TODO: Obtener lista de medios de pagos disponibles, según la situación de la cesta
//TODO: Obtener lista de tipos de servicios
//TODO: Compatibilidad del tipo de documento destino con el estado de la cesta (por ejemplo, cuestiones fiscales).
public class BasketPaymentsManager {
	private static final Logger log = Logger.getLogger(BasketPaymentsManager.class);

	@Autowired
	PayTransactionService payTransactionsService;
	
	protected BasketManager basketManager;
	
	@Autowired
	PaymentsManager paymentsManager;

	protected void setBaskeManager(BasketManager basketManager) {
		this.basketManager = basketManager;

		paymentsManager.setSesionCaja(basketManager.getSesion().getSesionCaja());
		
		//PaymentsManager paymentsManager = basketManager.getPaymentsManager();

		paymentsManager.addListenerOk(new PaymentsOkListener() {
			@Override
			public void paymentsOk(PaymentsOkEvent event) {
				PaymentOkEvent eventOk = event.getOkEvent();
				processPaymentOk(eventOk);
			}
		});

		paymentsManager.addListenerPaymentCompleted(new PaymentsCompletedListener() {
			@Override
			public void paymentsCompleted(PaymentsCompletedEvent event) {
				//finishSale(event);
			}
		});

		paymentsManager.addListenerError(new PaymentsErrorListener() {
			@Override
			public void paymentsError(PaymentsErrorEvent event) {
				processPaymentError(event);
			}
		});
	}

	@Transactional(rollbackFor = Exception.class)
	public PaymentTransactionLineEntity payRequest(TenderDTO message) throws ApiException {		
//		PaymentsManager paymentsManager = basketManager.getPaymentsManager();
		
		if (basketManager.getBasketTransaction().getCurrentTillTransactionDetailId() != null) {
			throw new ApiException(ApiException.STATUS_RESPONSE_ERROR_CONFLICT_STATE, "Other pay request is active");
		}

		// activate tender mode
		if (!basketManager.getBasketTransaction().getTenderMode()) {
			basketManager.getBasketTransaction().setTenderMode(true);
		}
		
		PayTransactionEntity tillTransaction;
		
		//TODO: parametro currency
		if (basketManager.getBasketTransaction().getTillTransactionId() == null) {
			// first basket till transaction
			tillTransaction = payTransactionsService.newTransaction(basketManager.getSesion(), basketManager.getBasketTransaction().getId(), basketManager.getBasketTransaction().getTotals().getBalanceDue(), null);
			
			basketManager.getBasketTransaction().setTillTransactionId(tillTransaction.getPayTransactionUid());			
		} else {
			// get current till transaction
			tillTransaction = payTransactionsService.getTransaction(basketManager.getSesion(), basketManager.getBasketTransaction().getTillTransactionId());
			
			if (tillTransaction.getStatusId().compareTo(PayTransactionService.STATUS_PENDING) != 0) {
				throw new ApiException(ApiException.STATUS_RESPONSE_ERROR_CONFLICT_STATE, "The current till transaction state is incorrect");				
			}
		}
			
		PaymentTransactionLineEntity newTransactionDetail = payTransactionsService.beginPaymentTransaction(basketManager.getSesion(), tillTransaction, message.getPaymentMethodCode(), message.getAmount(), message.getAdditionalData());
		basketManager.getBasketTransaction().setCurrentTillTransactionDetailId(newTransactionDetail.getPayTransactionLineUid());
		basketManager.persistBasket();
		
		paymentsManager.setTicketData(basketManager.getBasketCalculator(), null);
		
		try {
			paymentsManager.pay(message.getPaymentMethodCode(), message.getAmount());
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					paymentsManager.pay(message.getPaymentMethodCode(), message.getAmount());
//				}
//			}).start();
		} catch (Exception e) {
			if (e instanceof PaymentException) {
				PaymentErrorEvent errorEvent = new PaymentErrorEvent(this, ((PaymentException) e).getPaymentId(), e,
						((PaymentException) e).getErrorCode(), ((PaymentException) e).getMessage());
				PaymentsErrorEvent event = new PaymentsErrorEvent(this, errorEvent);
				paymentsManager.getEventsHandler().paymentsError(event);

			} else {
				PaymentErrorEvent errorEvent = new PaymentErrorEvent(this, -1, e, null, null);
				PaymentsErrorEvent event = new PaymentsErrorEvent(this, errorEvent);
				paymentsManager.getEventsHandler().paymentsError(event);
			}
		}
		
		return newTransactionDetail;
	}

	protected TenderAcceptedDTO processPaymentOk(PaymentOkEvent eventOk) {
		log.debug("processPaymentOk() - Pay accepted");
		
		try {
			payTransactionsService.endPaymentTransaction(basketManager.getSesion(), basketManager.getBasketTransaction().getCurrentTillTransactionDetailId(), eventOk.getExtendedData().toString().getBytes());
		} catch (ApiException e) {
			throw new RuntimeException(e);
		}
		
		return basketManager.addPayToBasket(eventOk, true);
	}

	protected EndTransactionDTO finishSale(final PaymentsCompletedEvent event) {
		log.debug("finishSale() - Printing receipt and end trasaction");

		basketManager.saveSalesDocument();

		// print receipt
		// printReceipt();

		// end transaction
		EndTransactionDTO message = new EndTransactionDTO();
		message.setId(basketManager.getBasketTransaction().getId());

		// itemsManager.resetTicket();
		return message;
	}

	protected TenderExceptionDTO processPaymentError(PaymentsErrorEvent event) {
		log.debug("processPaymentError() - Pay rejected!!!");

		try {
			payTransactionsService.endPaymentTransactionWithKO(basketManager.getSesion(), basketManager.getBasketTransaction().getCurrentTillTransactionDetailId(), event.getErrorEvent().getErrorMessage().getBytes());
		} catch (ApiException e) {
			throw new RuntimeException(e);
		}
		
		basketManager.getBasketTransaction().setCurrentTillTransactionDetailId(null);
		try {
			basketManager.persistBasket();
		} catch (ApiException e) {
			throw new RuntimeException(e);
		}

		PaymentErrorEvent errorEvent = event.getErrorEvent();

		TenderExceptionDTO message = new TenderExceptionDTO();
		message.setExceptionId("0");
		message.setExceptionType("0");

		if (event.getSource() instanceof PaymentMethodManager) {
			PaymentMethodManager paymentMethodManager = (PaymentMethodManager) event.getSource();

			message.setPaymentMethodCode(paymentMethodManager.getPaymentCode());
		} else {
			message.setPaymentMethodCode("Credit");
		}

		if (errorEvent.getException() != null) {
			message.setMessage(errorEvent.getException().getMessage());
		} else {
			message.setMessage(errorEvent.getErrorMessage());
		}

		return message;
	}

	public PaymentsStatusDto getPaymentStatus() {
		PaymentsStatusDto status = new PaymentsStatusDto();
		status.setPayments(basketManager.getBasketTransaction().getPayments());
		status.setTotals(basketManager.getBasketTransaction().getTotals());
		status.setTillTransactionId(basketManager.getBasketTransaction().getTillTransactionId());
		status.setCurrentTillTransactionDetailId(basketManager.getBasketTransaction().getCurrentTillTransactionDetailId());
		return status;
	}

}
