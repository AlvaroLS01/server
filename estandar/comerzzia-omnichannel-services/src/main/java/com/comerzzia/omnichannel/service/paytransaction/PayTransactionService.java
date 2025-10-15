package com.comerzzia.omnichannel.service.paytransaction;

import java.math.BigDecimal;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.core.basketcalculator.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.dto.paytransaction.PayTransactionDTO;
import com.comerzzia.omnichannel.domain.entity.paytransaction.PayTransactionEntity;
import com.comerzzia.omnichannel.domain.entity.paytransactionline.PaymentTransactionLineEntity;
import com.comerzzia.pos.services.core.sesion.Sesion;

public interface PayTransactionService {
	public static final Short STATUS_PENDING 	  = 0;
	
	public static final Short STATUS_IN_PROGRESS  = 5;
	
	public static final Short STATUS_PRE_ACCEPTED = 8;	
	public static final Short STATUS_OK 		  = 10;
	
	public static final Short STATUS_KO 		  = -10;
	public static final Short STATUS_DELETED 	  = -20;
	public static final Short STATUS_CANCELED 	  = -30;

	PayTransactionEntity newTransaction(Sesion sesion, String documentId, BigDecimal totalAmount, String currencyId);

	PayTransactionEntity getTransaction(IDatosSesion datosSesion, String transactionId);

	PayTransactionDTO getTransactionDTO(IDatosSesion datosSesion, String transactionId);

	PaymentTransactionLineEntity getTransactionDetail(IDatosSesion datosSesion, String transactionDetailId);

	PaymentTransactionLineEntity beginPaymentTransaction(Sesion sesion, String transactionId, String paymentMethodCode,
			BigDecimal paymentAmount, byte[] transactionData) throws ApiException;

	PaymentTransactionLineEntity beginPaymentTransaction(Sesion sesion, PayTransactionEntity tillTransaction,
			String paymentMethodCode, BigDecimal paymentAmount, byte[] transactionData) throws ApiException;

	PaymentTransactionLineEntity registryPaymentTransaction(Sesion sesion, PayTransactionEntity tillTransaction,
			String paymentMethodCode, BigDecimal paymentAmount);

	PayTransactionEntity endPaymentTransaction(Sesion sesion, String paymentTransactionDetailId,
			byte[] transactionResponse) throws ApiException;

	PayTransactionEntity endPaymentTransactionWithKO(Sesion sesion, String paymentTransactionDetailId,
			byte[] transactionResponse) throws ApiException;

	PayTransactionEntity cancelPaymentTransaction(Sesion sesion, String paymentTransactionDetailId,
			byte[] transactionResponse) throws ApiException;

}