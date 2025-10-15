package com.comerzzia.omnichannel.service.documentvalidation.fiscal;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.firma.pt.HashSaftPt;
import com.comerzzia.omnichannel.domain.dto.fiscal.FiscalData;
import com.comerzzia.omnichannel.domain.dto.fiscal.FiscalDataProperty;
import com.comerzzia.omnichannel.service.documentvalidation.fiscal.ISaleDocumentFiscalProperties.Payment;
import com.comerzzia.omnichannel.service.documentvalidation.result.SaleDocumentDataValidationResultDTO;
import com.comerzzia.pos.persistence.mediosPagos.MedioPagoBean;
import com.comerzzia.pos.persistence.tickets.TicketBean;
import com.comerzzia.pos.services.core.documentos.DocumentoException;
import com.comerzzia.pos.services.core.documentos.Documentos;
import com.comerzzia.pos.services.mediospagos.MediosPagosService;
import com.comerzzia.pos.services.ticket.TicketsService;
import com.comerzzia.pos.services.ticket.TicketsServiceException;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.format.FormatUtil;
import com.comerzzia.pos.util.i18n.I18N;

import lombok.extern.log4j.Log4j;

@Component("defaultFiscalDataValidationService")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Log4j
public class DefaultFiscalDataValidationServiceImpl implements FiscalDataValidationService<ISaleDocumentFiscalProperties> {
    public static final String PROPERTY_SIGN = "sign";
    
    protected String activityUid;
    protected String countryCode;
    protected String docTypeCode;
    
    @Autowired
    protected Documentos documents;

    @Autowired
    protected MediosPagosService paymentMethodsService;

    @Autowired
    protected TicketsService ticketsService;

    public DefaultFiscalDataValidationServiceImpl(String activityUid, String countryCode, String docTypeCode) {
        this.activityUid = activityUid;
        this.countryCode = countryCode;
        this.docTypeCode = docTypeCode;
    }

    @Override
    public SaleDocumentDataValidationResultDTO validateIssuance(ISaleDocumentFiscalProperties document) {
        log.debug("validateIssuance() - Comprobando si el documento es fiscalmente válido...");
        try {
            SaleDocumentDataValidationResultDTO validationResult = new SaleDocumentDataValidationResultDTO();

            validateMaxAllowedAmountWithoutTaxes(document, validationResult);
            validateMaxAllowedAmount(document, validationResult);
            validateMaxAllowedAmountPaymentMethod(document, validationResult);
            validateBillingData(document, validationResult);

            if (document.getSourceDocument() != null) {
                validateMaxReturnPeriod(document, validationResult);
                validateFiscalTreatmentReturn(document, validationResult);
            }

            validateFiscalDeviceisReady(validationResult);

            return validationResult;
        } catch (DocumentoException e) {
            String msg = "Ha ocurrido un error validando fiscalmente el documento: " + e.getMessage();
            log.error(msg, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public SaleDocumentDataValidationResultDTO validatePay(ISaleDocumentFiscalProperties document, ISaleDocumentFiscalProperties.Payment payment) {
        log.debug("validatePay() - Comprobando si el pago introducido es fiscalmente válido...");
        SaleDocumentDataValidationResultDTO validationResult = new SaleDocumentDataValidationResultDTO();
        
        if (getPaymentsMethodAvailables(document).contains(payment.getPaymentMethodCode())) {
			BigDecimal maxAllowedCashAmount = null;

            try {
    			if (countryCode.equals(document.getRecipient().getCountryCode())) {
                    maxAllowedCashAmount = documents.getDocumento(document.getDocTypeId()).getImporteMaximoEfectivo();
    			} else {
    				maxAllowedCashAmount = documents.getDocumento(document.getDocTypeId()).getImporteMaximoEfectivoEx(); 
    			}
            } catch (DocumentoException ignore) {
            } 
            
            //TODO: Ver como se suma solo lo efectivo

			if (maxAllowedCashAmount != null) {
				BigDecimal totalAmount = payment.getAmount();
				for (Payment actualPayment : document.getPayments()) {
					if (actualPayment.getPaymentMethodCode().equals(payment.getPaymentMethodCode())) {
						totalAmount = totalAmount.add(payment.getAmount().abs());
					}
				}
				if (BigDecimalUtil.isMayor(totalAmount, maxAllowedCashAmount)) {
                  validationResult.addError(I18N.getTexto("La cantidad que se quiere pagar con el medio de pago {0} supera el máximo permitido ({1})",
                          payment.getPaymentMethodDes(), maxAllowedCashAmount));
				}
			}
		} else {
            validationResult.addError(I18N.getTexto("El medio de pago seleccionado no está disponible"));
		}


        return validationResult;
    }

    @Override
    public SaleDocumentDataValidationResultDTO beginIssuance(ISaleDocumentFiscalProperties document) {
        if (!validateIssuance(document).isValid()) {
            throw new RuntimeException(I18N.getTexto("El documento no ha pasado la validación"));
        }
        
        log.debug("beginIssuance() - Preparando la emisión del documento...");
//TODO:		Dispositivos.getInstance().getFiscal().isReady();
        return null;
    }

    @Override
    public FiscalData endIssuance(ISaleDocumentFiscalProperties document) {
        log.debug("endIssuance() - Finalizando la emisión del documento...");
        FiscalData fiscalData = new FiscalData();
        List<FiscalDataProperty> properties = new ArrayList<FiscalDataProperty>();
        if (document.getSalesDocNumber() == null) {
            // TODO:
//			try {
//				ticketsService.setContadorIdTicket((Ticket) ticket);
//			} catch (TicketsServiceException e) {
//				String msg = "Ha ocurrido un error asignando un id al documento: " + e.getMessage();
//				log.error(msg, e);
//				throw new RuntimeException(e);
//			}
        }
        properties.add(new FiscalDataProperty(PROPERTY_SIGN, generateSignature(document)));
        fiscalData.setProperties(properties);
        return fiscalData;
    }

    @Override
    public void cancelIssuance(ISaleDocumentFiscalProperties document) {
        log.debug("cancelIssuance() - Cancelando la emisión del documento...");
//		TipoDocumentoBean documentType = null;
//		try {
//			documentType = documents.getDocumento(ticket.getCabecera().getTipoDocumento());
//		} catch (Exception e) {
//		}
//
//		TipoDocumentoBean documentTypeSource = null;
//		if (sourceTicket != null) {
//			try {
//				documentTypeSource = documents.getDocumento(sourceTicket.getCabecera().getTipoDocumento());
//			} catch (Exception e) {
//			}
//		}
//
//		ticketsService.saveEmptyTicket(ticket, documentType, documentTypeSource);
    }

    @Override
    public void confirmIssuance(ISaleDocumentFiscalProperties document) {
        log.debug("confirmIssuance() - Confirmando la emisión del documento...");
    }

    @Override
    public Set<String> getPaymentsMethodAvailables(ISaleDocumentFiscalProperties document) {
        // TODO: Si el documento tiene documento origen, se deberían de quedar disponibles solo los medios de pago utilizados en el
        return null;
    }

    protected void validateMaxAllowedAmountWithoutTaxes(ISaleDocumentFiscalProperties document, SaleDocumentDataValidationResultDTO validationResult) throws DocumentoException {
        BigDecimal maxAllowedAmount = documents.getDocumento(document.getDocTypeId()).getImporteMaximoSinImpuestos();

        if (maxAllowedAmount != null && BigDecimalUtil.isMayor(document.getTotals().getBaseAmount(), maxAllowedAmount)) {
            validationResult.addError(I18N.getTexto("El total sin impuestos de la venta supera el máximo permitido ({0}) para el tipo de documento: {1}",
                    FormatUtil.getInstance().formateaImporte(maxAllowedAmount), docTypeCode));
        }
    }

    protected void validateMaxAllowedAmount(ISaleDocumentFiscalProperties document, SaleDocumentDataValidationResultDTO validationResult) throws DocumentoException {
        BigDecimal maxAllowedAmount = documents.getDocumento(document.getDocTypeId()).getImporteMaximo();

        if (maxAllowedAmount != null && BigDecimalUtil.isMayor(document.getTotals().getTotalAmount(), maxAllowedAmount)) {

            validationResult.addError(I18N.getTexto("El total con impuestos de la venta supera el máximo permitido ({0}) para el tipo de documento: {1}",
                    FormatUtil.getInstance().formateaImporte(maxAllowedAmount), docTypeCode));
        }
    }

    protected void validateMaxReturnPeriod(ISaleDocumentFiscalProperties document, SaleDocumentDataValidationResultDTO validationResult) throws DocumentoException {
        if (document.getSourceDocument().getDocumentDate() == null)
            return;

        Integer maxReturnPeriod = documents.getDocumento(document.getSourceDocument().getDocTypeId()).getDiasMaximoDevolucion();

        if (maxReturnPeriod != null) {
            Date sourceDate = document.getSourceDocument().getDocumentDate();

            long difference = new Date().getTime() - sourceDate.getTime();
            long days = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);

            if (days > maxReturnPeriod) {
                validationResult.addError(I18N.getTexto("La devolución no se puede hacer porque superar el plazo máximo de devolución"));
            }
        }
    }

    protected void validateFiscalTreatmentReturn(ISaleDocumentFiscalProperties document, SaleDocumentDataValidationResultDTO validationResult) {
        Long taxTreatmentSourceId = document.getSourceDocument().getTaxTreatmentId();

        if (taxTreatmentSourceId != null) {
            Long taxTreatmentTicketId = document.getTaxTreatmentId();

            if (!taxTreatmentSourceId.equals(taxTreatmentTicketId)) {
                validationResult.addError(I18N.getTexto("El ticket fue realizando en una tienda con un tratamiento fiscal diferente al de esta tienda. No se puede realizar esta devolución"));
            }
        }

    }

    protected void validateMaxAllowedAmountPaymentMethod(ISaleDocumentFiscalProperties document, SaleDocumentDataValidationResultDTO validationResult) {

        Map<String, BigDecimal> mapAmounts = new HashMap<String, BigDecimal>();

        for (Payment payment : document.getPayments()) {
            BigDecimal amountPaymentMethod = mapAmounts.get(payment.getPaymentMethodCode());
            if (amountPaymentMethod == null) {
                amountPaymentMethod = BigDecimal.ZERO;
            }
            amountPaymentMethod = amountPaymentMethod.add(payment.getAmount());
            mapAmounts.put(payment.getPaymentMethodCode(), amountPaymentMethod);
        }

        boolean isForeign = !StringUtils.equals(document.getSeller().getCountryCode(), document.getRecipient().getCountryCode());

        for (Entry<String, BigDecimal> entryMapAmounts : mapAmounts.entrySet()) {
            String paymentMethodCode = entryMapAmounts.getKey();

            BigDecimal maxAllowedAmountPaymentMethod = null;

            if (isForeign) {
                maxAllowedAmountPaymentMethod = BigDecimal.ZERO; // TODO:.getMaxAmountForeign();
            } else {
                maxAllowedAmountPaymentMethod = BigDecimal.ZERO; // TODO:.getMaxAmount();
            }

            if (maxAllowedAmountPaymentMethod != null) {
                BigDecimal amount = entryMapAmounts.getValue();
                if (BigDecimalUtil.isMayor(amount, maxAllowedAmountPaymentMethod)) {
                    MedioPagoBean paymentMethod = paymentMethodsService.getMedioPago(paymentMethodCode);
                    
                    validationResult.addError(I18N.getTexto("La cantidad que se quiere pagar con el medio de pago {0} supera el máximo permitido ({1})",
                            paymentMethod.getDesMedioPago(), maxAllowedAmountPaymentMethod));
                }
            }
        }

    }

    protected void validateBillingData(ISaleDocumentFiscalProperties document, SaleDocumentDataValidationResultDTO validationResult) {
        if (docTypeCode.equals(Documentos.FACTURA_COMPLETA)
                || (document.getSourceDocument() != null && document.getSourceDocument().getDocTypeCode().equals(Documentos.FACTURA_COMPLETA))) {
            
            if (document.getRecipient() == null) {
                validationResult.addError(I18N.getTexto("El tipo de documento es una factura. Se deben especificar los datos de la factura"));
            }
        }
    }

    protected void validateFiscalDeviceisReady(SaleDocumentDataValidationResultDTO validationResult) {
//		if(!Dispositivos.getInstance().getFiscal().isReady()) {
//			FiscalDataValidationMessageDTO message = new FiscalDataValidationMessageDTO();
//			message.setMessageType(FiscalDataValidationMessageDTO.Type.ERROR);
//			message.setMessageInfo(I18N.getTexto("El dispositivo fiscal no está preparado"));
//			messages.add(message);
//		}
    }

    protected String generateSignature(ISaleDocumentFiscalProperties document) {
        log.debug("generateSignature()");

        String signature = "";

        String formattedDate = FormatUtil.getInstance().formateaFechaHoraTicket(document.getDocumentDate());

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(); // Modificamos el separador para Portugal
        symbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("0.00", symbols);
        String totalTicket = decimalFormat.format(document.getTotals().getTotalAmount().abs()); // siempre en valor absoluto

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String simpleDate = simpleDateFormat.format(document.getDocumentDate());
        try {
            TicketBean lastTicket = ticketsService.consultarUltimoTicketFirma(activityUid, document.getSalesDocNumber(), document.getDocTypeId(), document.getSalesDocSerial());

            String lastTicketSignature = "";

            if (lastTicket != null) {
                lastTicketSignature = lastTicket.getFirma();
            }

            String signatureString = simpleDate + ";" + formattedDate + ";" + document.getSalesDocCode() + ";" + totalTicket + ";" + lastTicketSignature;

            signature = HashSaftPt.firma(signatureString);
        } catch (TicketsServiceException e) {
            log.error("generateSignature() - Ha ocurrido un error generando la firma del documento: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return signature;
    }
}
