package com.comerzzia.omnichannel.service.till;

import java.util.List;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.omnichannel.domain.dto.paymentmethod.PaymentMethodDTO;
import com.comerzzia.pos.services.cajas.CashJournalDTO;
import com.comerzzia.pos.services.core.sesion.Sesion;

public interface TillService {

	List<PaymentMethodDTO> getAvailablePaymentMethods(Sesion sesion);
	
	void print(Sesion sesion, CashJournalDTO caja) throws ApiException;	
	void printLastTillClosure(Sesion sesion) throws ApiException;
}