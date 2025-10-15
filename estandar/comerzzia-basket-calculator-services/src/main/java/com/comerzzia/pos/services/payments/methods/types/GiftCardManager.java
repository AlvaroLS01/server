package com.comerzzia.pos.services.payments.methods.types;

import java.math.BigDecimal;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.persistence.giftcard.GiftCardBean;
import com.comerzzia.pos.services.core.sesion.Sesion;
import com.comerzzia.pos.services.core.variables.VariablesServices;
import com.comerzzia.pos.services.payments.PaymentDto;
import com.comerzzia.pos.services.payments.PaymentException;
import com.comerzzia.pos.services.payments.configuration.PaymentMethodConfiguration;
import com.comerzzia.pos.services.payments.events.PaymentErrorEvent;
import com.comerzzia.pos.services.payments.events.PaymentInitEvent;
import com.comerzzia.pos.services.payments.events.PaymentOkEvent;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.i18n.I18N;

@Component
@Scope("prototype")
public class GiftCardManager extends BasicPaymentMethodManager {

	public static final String PARAM_TARJETA = "PARAM_TARJETA";

	private Logger log = Logger.getLogger(GiftCardManager.class);
	    
    @Autowired
    Sesion sesion;
	
	@Autowired
	private VariablesServices variablesServices;

	@Override
	public void setConfiguration(PaymentMethodConfiguration configuration) {
		log.debug("setConfiguration() - Cargando configuración para el medio de pago: " + paymentCode);
	}

	@Override
	public boolean pay(BigDecimal amount) throws PaymentException {
		try {
			PaymentInitEvent initEvent = new PaymentInitEvent(this);
			getEventHandler().paymentInitProcess(initEvent);
			
			GiftCardBean giftCard = (GiftCardBean) parameters.get(PARAM_TARJETA);
			
			String uidTransaccion = UUID.randomUUID().toString();
			
			if(BigDecimalUtil.isMayor(amount, ticket.getTotales().getPendiente())) {
				String mensaje = I18N.getTexto("El importe indicado supera el importe pendiente de la venta.");
				throw new PaymentException(mensaje);
			}
			
			if(BigDecimalUtil.isMayor(amount, giftCard.getSaldo())) {
				String mensaje = I18N.getTexto("El importe indicado supera el saldo de la tarjeta.");
				throw new PaymentException(mensaje);
			}
			
//			ListaMovimientoRequestRest request = createProvisionalMovements(amount, giftCard, true, uidTransaccion);
//			MovimientosRest.crearMovimientosTarjetaRegaloProvisionales(request);
			
			PaymentOkEvent event = new PaymentOkEvent(this, paymentId, amount);
			
			giftCard.setUidTransaccion(uidTransaccion);
			event.addExtendedData(PARAM_TARJETA, giftCard);
			
			getEventHandler().paymentOk(event);
			
			return true;
		}
		catch (Exception e) {
			log.error("pay() - Ha habido un error al realizar el pago con tarjeta regalo: " + e.getMessage(), e);
			
			PaymentErrorEvent event = new PaymentErrorEvent(this, paymentId, e, null, e.getMessage());
			getEventHandler().paymentError(event);
			
			return false;
		}
	}

//	protected ListaMovimientoRequestRest createProvisionalMovements(BigDecimal amount, GiftCardBean giftCard, boolean isPay, String uidTransacción)  {
//		List<MovimientoRequestRest> movimientos = new ArrayList<>();
//		
//		MovimientoRequestRest mov = new MovimientoRequestRest();
//		mov.setUidActividad(sesion.getUidActividad());
//		mov.setNumeroTarjeta(giftCard.getNumTarjetaRegalo());
//		mov.setConcepto(ticket.getCabecera().getDesTipoDocumento() + " " + ticket.getCabecera().getCodTicket());
//		mov.setUidTransaccion(uidTransacción);
//		mov.setFecha(new Date());
//		mov.setDocumento(String.valueOf(ticket.getIdTicket()));
//		mov.setApiKey(variablesServices.getVariableAsString(Variables.WEBSERVICES_APIKEY));
//		mov.setSaldo(giftCard.getSaldo().doubleValue());
//		mov.setSaldoProvisional(giftCard.getSaldoProvisional().doubleValue());
//		
//		if(isPay) {
//			mov.setSalida(amount.doubleValue());
//			mov.setEntrada(0.0);
//		}
//		else {
//			mov.setSalida(0.0);
//			mov.setEntrada(amount.doubleValue());
//		}
//		
//		movimientos.add(mov);
//		
//		ListaMovimientoRequestRest request = new ListaMovimientoRequestRest();
//		request.setMovimientos(movimientos);
//		
//		return request;
//	}

	@Override
	public boolean returnAmount(BigDecimal amount) throws PaymentException {
		try {
			PaymentInitEvent initEvent = new PaymentInitEvent(this);
			getEventHandler().paymentInitProcess(initEvent);
			
			GiftCardBean giftCard = (GiftCardBean) parameters.get(PARAM_TARJETA);
			String uidTransaccion = UUID.randomUUID().toString();
			
//			ListaMovimientoRequestRest request = createProvisionalMovements(amount, giftCard, false, uidTransaccion);
//			MovimientosRest.crearMovimientosTarjetaRegaloProvisionales(request);
			
			PaymentOkEvent event = new PaymentOkEvent(this, paymentId, amount);
			
			giftCard.setUidTransaccion(uidTransaccion);
			event.addExtendedData(PARAM_TARJETA, giftCard);
			getEventHandler().paymentOk(event);
			
			return true;
		}
		catch (Exception e) {
			log.error("pay() - Ha habido un error al realizar el pago con tarjeta regalo: " + e.getMessage(), e);
			
			PaymentErrorEvent event = new PaymentErrorEvent(this, paymentId, e, null, e.getMessage());
			getEventHandler().paymentError(event);
			
			return false;
		}
	}

	@Override
	public boolean cancelPay(PaymentDto paymentDto) throws PaymentException {
		try {			
			GiftCardBean giftCard = (GiftCardBean) paymentDto.getExtendedData().get(PARAM_TARJETA);
			String uidTransaccion = giftCard.getUidTransaccion();
			
//			ListaMovimientoRequestRest request = createProvisionalMovements(paymentDto.getAmount(), giftCard, true, uidTransaccion);
//        	MovimientosRest.anularMovimientosProvisionalesTarjetaRegalo(request);
			
			PaymentOkEvent event = new PaymentOkEvent(this, paymentDto.getPaymentId(), paymentDto.getAmount());
			event.setCanceled(true);
			getEventHandler().paymentOk(event);
			
			return true;
		}
		catch (Exception e) {
			log.error("cancelPay() - Ha habido un error al cancelar el pago con tarjeta regalo: " + e.getMessage(), e);
			
			PaymentErrorEvent event = new PaymentErrorEvent(this, paymentId, e, null, e.getMessage());
			getEventHandler().paymentError(event);
			
			return false;
		}
	}

	@Override
	public boolean cancelReturn(PaymentDto paymentDto) throws PaymentException {
		try {			
			GiftCardBean giftCard = (GiftCardBean) paymentDto.getExtendedData().get(PARAM_TARJETA);
			String uidTransaccion = giftCard.getUidTransaccion();
			
//			ListaMovimientoRequestRest request = createProvisionalMovements(paymentDto.getAmount(), giftCard, true, uidTransaccion);
//        	MovimientosRest.anularMovimientosProvisionalesTarjetaRegalo(request);
			
			PaymentOkEvent event = new PaymentOkEvent(this, paymentDto.getPaymentId(), paymentDto.getAmount());
			event.setCanceled(true);
			getEventHandler().paymentOk(event);
			
			return true;
		}
		catch (Exception e) {
			log.error("cancelReturn() - Ha habido un error al cancelar el pago con tarjeta regalo: " + e.getMessage(), e);
			
			PaymentErrorEvent event = new PaymentErrorEvent(this, paymentId, e, null, e.getMessage());
			getEventHandler().paymentError(event);
			
			return false;
		}
	}
	
	@Override
	public boolean isAvailableForExchange() {
		return false;
	}

}
