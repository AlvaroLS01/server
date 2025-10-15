package com.comerzzia.omnichannel.service.till;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.omnichannel.domain.dto.paymentmethod.PaymentMethodDTO;
import com.comerzzia.pos.persistence.mediosPagos.MedioPagoBean;
import com.comerzzia.pos.services.cajas.CashJournalDTO;
import com.comerzzia.pos.services.cajas.CajaEstadoException;
import com.comerzzia.pos.services.cajas.CajasService;
import com.comerzzia.pos.services.cajas.CajasServiceException;
import com.comerzzia.pos.services.core.sesion.Sesion;
import com.comerzzia.pos.services.payments.PaymentsManager;
import com.comerzzia.pos.util.i18n.I18N;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class TillServiceImpl implements TillService {
		
	@Autowired
	private CajasService cajasService;
	
	@Autowired
	PaymentsManager paymentsManager;

	@Override
	public List<PaymentMethodDTO> getAvailablePaymentMethods(Sesion sesion) {
		List<PaymentMethodDTO> result = new ArrayList<PaymentMethodDTO>();
		
		String defaultPaymentMethod = sesion.getSesionCaja().getMedioPagoDefecto().getCodMedioPago();
		//PaymentsManager paymentsManager = sesion.getSesionCaja().getPaymentsManager();
		paymentsManager.setSesionCaja(sesion.getSesionCaja());
		
		// for every sales payment method
		for (MedioPagoBean medioPago : sesion.getSesionCaja().getPaymentMethodsData().getMediosPagoVisibleVenta()) {
			if (paymentsManager.isPaymentMethodAvailable(medioPago.getCodMedioPago())) {
				PaymentMethodDTO paymentMethod = new PaymentMethodDTO();
				paymentMethod.setPaymentMethodCode(medioPago.getCodMedioPago());
				paymentMethod.setPaymentMethodDes(medioPago.getDesMedioPago());
				paymentMethod.setAvailable(true);
				paymentMethod.setAvailableForExchange(paymentsManager.isExchangePaymentMethodAvailable(medioPago.getCodMedioPago()));
				paymentMethod.setAvailableForReturn(paymentsManager.isPaymentMethodAvailableForReturn(medioPago.getCodMedioPago()));
				paymentMethod.setCash(medioPago.getEfectivo());
				paymentMethod.setCreditCard(medioPago.getTarjetaCredito());
				paymentMethod.setDefaultMethod(StringUtils.equals(defaultPaymentMethod, medioPago.getCodMedioPago()));
				
				result.add(paymentMethod);
			}			
		}
		
		return result;
	}
	
	@Override
	public void print(Sesion sesion, CashJournalDTO caja) throws ApiException {

	}
	
	
	public void printLastTillClosure(Sesion sesion) throws ApiException {
		log.debug("accionImprimirCierreCaja()");
		try {
			CashJournalDTO caja = cajasService.consultarUltimaCajaCerrada();
			cajasService.consultarMovimientos(caja);
			cajasService.consultarRecuento(caja);
			cajasService.consultarTotales(caja);
			caja.recalculateTotals();
			caja.recalculateCountTotals();
			print(sesion, caja);
		}
		catch (CajasServiceException | CajaEstadoException e) {
			throw new ApiException(I18N.getTexto("Error recuperando el último cierre de caja para su impresión."), e);
		}
	}

}
