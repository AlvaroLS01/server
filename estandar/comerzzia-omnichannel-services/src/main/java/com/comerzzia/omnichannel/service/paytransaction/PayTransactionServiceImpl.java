package com.comerzzia.omnichannel.service.paytransaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.exception.NotFoundException;
import com.comerzzia.core.basketcalculator.servicios.sesion.IDatosSesion;
import com.comerzzia.omnichannel.domain.dto.paytransaction.PayTransactionDTO;
import com.comerzzia.omnichannel.domain.entity.paytransaction.PayTransactionEntity;
import com.comerzzia.omnichannel.domain.entity.paytransaction.PayTransactionKey;
import com.comerzzia.omnichannel.domain.entity.paytransactionline.PaymentTransactionLineEntity;
import com.comerzzia.omnichannel.domain.entity.paytransactionline.PaymentTransactionLineKey;
import com.comerzzia.omnichannel.repository.paytransaction.PayTransactionMapper;
import com.comerzzia.omnichannel.repository.paytransactionline.PaymentTransactionLineMapper;
import com.comerzzia.pos.services.core.sesion.Sesion;

//TODO: Actualización del texto del mensaje (por ejemplo, introduzca su tarjeta
//TODO: Implementación de timeouts de transacciones de pago

@Service
@Transactional(rollbackFor = Exception.class)
public class PayTransactionServiceImpl implements PayTransactionService {
    @Autowired
    PayTransactionMapper mapper;
    
    @Autowired
    PaymentTransactionLineMapper detailMapper;
    
    @Autowired
    ModelMapper modelMapper;
    
	@Override
	public PayTransactionEntity newTransaction(Sesion sesion, String documentId, BigDecimal totalAmount, String currencyId) {
		PayTransactionEntity result = new PayTransactionEntity();
		result.setPayTransactionUid(UUID.randomUUID().toString());
		result.setPayTransactionUidLink(result.getPayTransactionUid());
		result.setActivityId(sesion.getUidActividad());
		result.setSalesDocUid(documentId);
		result.setPayTransactionDate(new Date());
		result.setAmount(totalAmount);
		result.setCurrencyCode(currencyId);
		result.setPosUid(sesion.getAplicacion().getTerminalId());
		
		return result;
	}
	
	@Override
	public PayTransactionEntity getTransaction(IDatosSesion datosSesion, String transactionId) {
		return mapper.selectByPrimaryKey(new PayTransactionKey(datosSesion.getUidActividad(), transactionId));
	}
	
	@Override
	public PayTransactionDTO getTransactionDTO(IDatosSesion datosSesion, String transactionId) {
		PayTransactionDTO result = modelMapper.map(getTransaction(datosSesion, transactionId), PayTransactionDTO.class);
				
		// get transaction details
		result.setDetails(detailMapper.selectByTransactionUid(datosSesion.getUidActividad(), transactionId));
		
		// calculate balance
		BigDecimal payments = BigDecimal.ZERO;
		
		for (PaymentTransactionLineEntity detail : result.getDetails()) {
			if (detail.getStatusId().compareTo(PayTransactionService.STATUS_OK)  == 0) {
				payments = payments.add(detail.getAmount());
			}
		}
		
		result.setBalance(result.getAmount().subtract(payments));				
		
		return result;
	}
	
	@Override
	public PaymentTransactionLineEntity getTransactionDetail(IDatosSesion datosSesion, String transactionDetailId) {
		PaymentTransactionLineKey key = new PaymentTransactionLineKey(datosSesion.getUidActividad(), transactionDetailId);
		PaymentTransactionLineEntity transactionDetail = detailMapper.selectByPrimaryKey(key);		
		return transactionDetail;
	}
	
	protected Short getTransactionDetailOrder(IDatosSesion datosSesion, String transactionId) {
		int rows = detailMapper.countTransactionLines(datosSesion.getUidActividad(), transactionId);
		
		return new Integer(rows +1).shortValue();
	}
	
	protected PayTransactionDTO updatePaymentTransactionStatus(IDatosSesion datosSesion, String transactionId) throws ApiException {
		PayTransactionDTO record = getTransactionDTO(datosSesion, transactionId);
		if (record == null) {
			throw new NotFoundException();
		}
		
		// new record values
		PayTransactionEntity newRecord = new PayTransactionEntity();
		newRecord.setActivityId(record.getActivityId());
		newRecord.setPayTransactionUid(record.getPayTransactionUid());
		newRecord.setPayTransactionUidLink(record.getPayTransactionUidLink());
		
		// check balance
		if (record.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
			newRecord.setStatusId(PayTransactionService.STATUS_OK);			
		} else {
			// check details records status for pending transactions
			newRecord.setStatusId(PayTransactionService.STATUS_PENDING);
			
			for (PaymentTransactionLineEntity detail : record.getDetails()) {
				if (detail.getStatusId().compareTo(PayTransactionService.STATUS_IN_PROGRESS)  == 0) {
					newRecord.setStatusId(PayTransactionService.STATUS_IN_PROGRESS);
					break;
				}
			}
		}
		
		
		// status updated
		if (newRecord.getStatusId().compareTo(record.getStatusId()) != 0) {
		   if (mapper.updateTransactionStatus(record.getActivityId(),
				                              record.getPayTransactionUid(),
				                              record.getStatusId(),
				                              newRecord.getStatusId(),
				                              newRecord.getPayTransactionUidLink()) != 1) {
			   throw new ApiException(ApiException.STATUS_RESPONSE_ERROR_CONFLICT_STATE, "Error updating transaction status. Concurrence error detected.");
		   }
		   
		   record.setStatusId(newRecord.getStatusId());
		}
		
		return record;
	}
	
	@Override
	public PaymentTransactionLineEntity beginPaymentTransaction(Sesion sesion, String transactionId, String paymentMethodCode, BigDecimal paymentAmount, byte[] transactionData) throws ApiException {
		PayTransactionEntity record = getTransaction(sesion, transactionId);
		return beginPaymentTransaction(sesion, record, paymentMethodCode, paymentAmount, transactionData);
	}
	
	
	@Override
	public PaymentTransactionLineEntity beginPaymentTransaction(Sesion sesion, PayTransactionEntity tillTransaction, String paymentMethodCode, BigDecimal paymentAmount, byte[] transactionData) throws ApiException {
		boolean updateStatus = tillTransaction.getStatusId() != null;
		
		if (!updateStatus) {
			tillTransaction.setStatusId(PayTransactionService.STATUS_IN_PROGRESS);
			mapper.insert(tillTransaction);
		}
		
		PaymentTransactionLineEntity result = new PaymentTransactionLineEntity();
		result.setActivityId(tillTransaction.getActivityId());
		result.setPayTransactionUid(tillTransaction.getPayTransactionUid());
		result.setPayTransactionLineUid(UUID.randomUUID().toString());
		result.setLineId(getTransactionDetailOrder(sesion, tillTransaction.getPayTransactionUid()));
		
		result.setPaymentMethodCode(paymentMethodCode);
		result.setAmount(paymentAmount);
		result.setStartDate(new Date());

		if (transactionData != null) {
		   result.setTransactionData(new String(transactionData));
		}
		result.setStatusId(PayTransactionService.STATUS_IN_PROGRESS);
		
		detailMapper.insert(result);
		
		if (updateStatus) {
			updatePaymentTransactionStatus(sesion, tillTransaction.getPayTransactionUid());
		}

		return result;
	}
		
	@Override
	public PaymentTransactionLineEntity registryPaymentTransaction(Sesion sesion, PayTransactionEntity tillTransaction, String paymentMethodCode, BigDecimal paymentAmount) {
		if (tillTransaction.getStatusId() == null) {
			tillTransaction.setStatusId(PayTransactionService.STATUS_OK);
			mapper.insert(tillTransaction);
		}
		
		PaymentTransactionLineEntity result = new PaymentTransactionLineEntity();
		result.setActivityId(tillTransaction.getActivityId());
		result.setPayTransactionUid(tillTransaction.getPayTransactionUid());
		result.setPayTransactionLineUid(UUID.randomUUID().toString());
		result.setLineId(getTransactionDetailOrder(sesion, tillTransaction.getPayTransactionUid()));
		
		result.setPaymentMethodCode(paymentMethodCode);
		result.setAmount(paymentAmount);
		result.setStartDate(new Date());
		result.setEndDate(result.getStartDate());		
		
		result.setStatusId(PayTransactionService.STATUS_OK);
		
		detailMapper.insert(result);

		return result;
	}	

	protected PayTransactionEntity updatePaymentTransactionDetailStatus(Sesion sesion, String paymentTransactionDetailId, Short espectedStatus, Short newStatus, byte[]transactionResponse) throws ApiException {
		PaymentTransactionLineEntity currentRecord = getTransactionDetail(sesion, paymentTransactionDetailId);

		if (currentRecord == null) {
			throw new NotFoundException();
		}
		
		if (currentRecord.getStatusId().compareTo(espectedStatus) != 0) {
			throw new ApiException(ApiException.STATUS_RESPONSE_ERROR_CONFLICT_STATE, "Error updating payment transaction. The current transaction status " + currentRecord.getStatusId() + " is incorrect. Expedted status " + espectedStatus);
		}
		
		// update only necessary fields 
		PaymentTransactionLineEntity newRecord = new PaymentTransactionLineEntity();
		newRecord.setEndDate(new Date());
		newRecord.setPaymentGatewayResponse(new String(transactionResponse));
		newRecord.setStatusId(newStatus);

		// update with concurrence control for state
		if (detailMapper.updateTransactionLineStatus(currentRecord.getActivityId(),
				                                     currentRecord.getPayTransactionLineUid(),
				                                     espectedStatus,
				                                     newStatus,
				                                     new Date(),
				                                     new String(transactionResponse)) != 1) {
			throw new ApiException(ApiException.STATUS_RESPONSE_ERROR_CONFLICT_STATE, "Error updating transaction status. Concurrence error detected.");			
		}
		
		// update and return transaction global status
		return updatePaymentTransactionStatus(sesion, currentRecord.getPayTransactionUid());
	}
	
	@Override
	public PayTransactionEntity endPaymentTransaction(Sesion sesion, String paymentTransactionDetailId, byte[]transactionResponse) throws ApiException {
		return updatePaymentTransactionDetailStatus(sesion, paymentTransactionDetailId, PayTransactionService.STATUS_IN_PROGRESS, PayTransactionService.STATUS_OK, transactionResponse);
	}
	
	@Override
	public PayTransactionEntity endPaymentTransactionWithKO(Sesion sesion, String paymentTransactionDetailId, byte[]transactionResponse) throws ApiException {
		return updatePaymentTransactionDetailStatus(sesion, paymentTransactionDetailId, PayTransactionService.STATUS_IN_PROGRESS, PayTransactionService.STATUS_KO, transactionResponse);
	}
	
	@Override
	public PayTransactionEntity cancelPaymentTransaction(Sesion sesion, String paymentTransactionDetailId, byte[]transactionResponse) throws ApiException {
		return updatePaymentTransactionDetailStatus(sesion, paymentTransactionDetailId, PayTransactionService.STATUS_IN_PROGRESS, PayTransactionService.STATUS_CANCELED, transactionResponse);
	}
	
}
