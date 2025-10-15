package com.comerzzia.omnichannel.service.salesdocument.converters;

import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.MimeTypeUtils;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.exception.BadRequestException;
import com.comerzzia.core.model.tiposdocumentos.TipoDocumentoBean;
import com.comerzzia.core.model.usuarios.UsuarioBean;
import com.comerzzia.core.servicios.empresas.EmpresasService;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoException;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoNotFoundException;
import com.comerzzia.core.servicios.tipodocumento.TiposDocumentosService;
import com.comerzzia.core.servicios.usuarios.UsuarioException;
import com.comerzzia.core.servicios.usuarios.UsuarioNotFoundException;
import com.comerzzia.core.servicios.usuarios.UsuariosService;
import com.comerzzia.core.servicios.variables.VariablesService;
import com.comerzzia.omnichannel.domain.dto.item.ItemDTO;
import com.comerzzia.omnichannel.domain.dto.saledoc.PrintDocumentDTO;
import com.comerzzia.omnichannel.domain.dto.saledoc.SaleDocHdrDTO;
import com.comerzzia.omnichannel.domain.dto.saledoc.SaleDocLineDTO;
import com.comerzzia.omnichannel.domain.dto.saledoc.SaleDocLinePriceModDTO;
import com.comerzzia.omnichannel.domain.dto.saledoc.SaleDocPaymentDTO;
import com.comerzzia.omnichannel.domain.dto.saledoc.SaleDocTaxDTO;
import com.comerzzia.omnichannel.domain.entity.document.DocumentEntity;
import com.comerzzia.omnichannel.model.documents.sales.ticket.TicketVentaAbono;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.SubtotalIvaTicket;
import com.comerzzia.omnichannel.model.documents.sales.ticket.lineas.LineaTicket;
import com.comerzzia.omnichannel.model.documents.sales.ticket.lineas.PromocionLineaTicket;
import com.comerzzia.omnichannel.model.documents.sales.ticket.pagos.EntregaCuentaTicket;
import com.comerzzia.omnichannel.model.documents.sales.ticket.pagos.PagoTicket;
import com.comerzzia.omnichannel.model.documents.util.FormatUtil;
import com.comerzzia.omnichannel.service.documentprint.DocumentPrintService;
import com.comerzzia.omnichannel.service.item.ItemService;
import com.comerzzia.omnichannel.service.salesdocument.metadata.DocumentMetadata;
import com.comerzzia.omnichannel.service.salesdocument.metadata.DocumentMetadataParser;
import com.comerzzia.pos.persistence.paises.PaisMapper;

public abstract class AbstractTicketVentaAbonoConverter<T extends TicketVentaAbono> implements SaleDocumentConverter<T> {
	public static final String APPLIED_IN_LINE = "L";
	public static final String APPLIED_IN_HEADER = "C";
	public static final String APPLIED_IN_PAYMENT = "P";

	public static final String REASON_CODE_PROMOTION = "PROMOCION";
	public static final String REASON_CODE_COUPON = "CUPON";
	public static final String REASON_CODE_MANUAL_PRICE = "MANUAL";
	public static final String REASON_CODE_MANUAL_DISCOUNT = "DESCUENTO_MANUAL";

	@Autowired
	UsuariosService userService;

	@Autowired
	TiposDocumentosService tiposDocumentosService;
	
	@Autowired
	DocumentMetadataParser documentMetadataParser;	
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired 
	DocumentPrintService documentPrintService;
	
	@Autowired
	VariablesService variablesService;
	
	@Autowired
	EmpresasService empresasService;
	
	@Autowired
    protected PaisMapper paisMapper;
	
	@Autowired
	ItemService itemService;
	
    @Autowired
    protected ItemDTOConverter itemDTOConverter;
	
	@Override
	public SaleDocHdrDTO convert(final IDatosSesion datosSesion, final DocumentEntity document) {
		return convertHdr(datosSesion, convert(document.getDocumentContent()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public T convert(final byte[] content) {
		Class<T> genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractTicketVentaAbonoConverter.class);
		
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(genericType);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			return (T) jaxbUnmarshaller.unmarshal(new StringReader(new String(content, "UTF-8")));
		} catch (JAXBException | UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public DocumentMetadata getMetadata(IDatosSesion datosSesion, DocumentEntity document) {
		return documentMetadataParser.getMetadata(datosSesion, document);
	}
	
	@Override
	public void printDocument(OutputStream outputStream, IDatosSesion datosSesion, DocumentEntity document, PrintDocumentDTO printRequest) throws ApiException {
		// Prepare document data for print 
		prepareDocumentForPrint(datosSesion, document, printRequest);
				
		// Print
		documentPrintService.printToStream(outputStream, datosSesion, printRequest);
	}
	
	protected void checkMimeType(IDatosSesion datosSesion, DocumentEntity document, PrintDocumentDTO printRequest) throws ApiException {
		if (StringUtils.equals(printRequest.getMimeType(), MimeTypeUtils.APPLICATION_JSON_VALUE)) {
			throw new BadRequestException("Unsupported mime type '" + printRequest.getMimeType() + "' for this document type");
		}		
	}
	
	protected void prepareDocumentForPrint(IDatosSesion datosSesion, DocumentEntity document, PrintDocumentDTO printRequest) throws ApiException {
		if (StringUtils.isBlank(printRequest.getMimeType())) {
			printRequest.setMimeType(MimeTypeUtils.APPLICATION_XML_VALUE);
        }
		
		checkMimeType(datosSesion, document, printRequest);
		
		// Create document object
		DocumentMetadata documentMetadata = getMetadata(datosSesion, document);
		T documentObject = convert(document.getDocumentContent());
		
		// set format options by locale
		documentObject.setFormatUtil(new FormatUtil(documentMetadata.getLocale()));
		
		// update lines items auxiliary data
        for (LineaTicket linea : documentObject.getLineas()) {
           try {
               ItemDTO articulo = itemService.selectDTOByPrimaryKey(datosSesion.getUidActividad(), linea.getCodArticulo());
               
               linea.setArticulo(itemDTOConverter.convertToLineaTicketArticulo(datosSesion, articulo));
           } catch (Exception ignore) {
           }
        }
        
  	    // document params
		printRequest.getCustomParams().put("ticket", documentObject);
		printRequest.getCustomParams().put("document", documentObject);
		printRequest.getCustomParams().put(DocumentPrintService.PARAM_COMPANY_CODE, documentObject.getCabecera().getEmpresa().getCodEmpresa());
		printRequest.getCustomParams().put(DocumentPrintService.PARAM_LOCALE, documentMetadata.getLocale());
		
		// print template
		if (StringUtils.isEmpty(printRequest.getPrintTemplate())) {
			printRequest.setPrintTemplate(documentMetadata.getPrintTemplate());			
		}
		
		// path correction for compatibility
		if (StringUtils.isNotEmpty(printRequest.getPrintTemplate()) &&
			(StringUtils.equals(printRequest.getMimeType(), MimeTypeUtils.APPLICATION_XML_VALUE) ||
  		     StringUtils.equals(printRequest.getMimeType(), MimeTypeUtils.TEXT_HTML_VALUE))) {
			printRequest.setPrintTemplate(printRequest.getPrintTemplate());
		}
		
		// default template
 		if (StringUtils.isEmpty((String)printRequest.getCustomParams().get(DocumentPrintService.PARAM_DEFAULT_TEMPLATE))) {
 			printRequest.getCustomParams().put(DocumentPrintService.PARAM_DEFAULT_TEMPLATE, getDefaultTemplate(documentMetadata, printRequest.getMimeType()));
 		}
	}
	
	protected String getDefaultTemplate(DocumentMetadata metadata, String outputMimeType) {
		if (StringUtils.equals(outputMimeType, MimeTypeUtils.APPLICATION_XML_VALUE) ||
  		    StringUtils.equals(outputMimeType, MimeTypeUtils.TEXT_HTML_VALUE)) {
			return "factura";
		} 
		
		return metadata.getDocTypeCode();
	}

	protected SaleDocHdrDTO convertHdr(IDatosSesion datosSesion, final T source) {
		SaleDocHdrDTO result = new SaleDocHdrDTO();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(source.getCabecera().getFecha());
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

		result.setActivityUid(source.getCabecera().getUidActividad());
		result.setCompanyCode(source.getCabecera().getEmpresa().getCodEmpresa());

		result.setPeriod(calendar.get(Calendar.YEAR));
		result.setSerialCode(source.getCabecera().getTienda().getCodAlmacen());
		result.setSalesDocNumber(source.getCabecera().getIdTicket());
		result.setSalesDocSerial(source.getCabecera().getSerieTicket());
		result.setSalesDocUid(source.getCabecera().getUidTicket());
		result.setSalesDocCode(source.getCabecera().getCodTicket());

		result.setSalesDocDate(DateUtils.truncate(source.getCabecera().getFecha(), Calendar.DAY_OF_MONTH));
		result.setHour(sdf.format(source.getCabecera().getFecha()));
		result.setSupplyDate(source.getCabecera().getFechaContable());

		result.setStoreCode(source.getCabecera().getTienda().getCodAlmacen());
		result.setTillCode(source.getCabecera().getCodCaja());
		result.setCashJournalUid(source.getCabecera().getUidDiarioCaja());
		result.setDocTypeId(source.getCabecera().getTipoDocumento());

		try {
			TipoDocumentoBean tipoDocumentoBean = tiposDocumentosService.consultar(datosSesion,
					source.getCabecera().getTipoDocumento());
			result.setDocTypeCode(tipoDocumentoBean.getCodTipoDocumento());
			result.setDocTypeDes(tipoDocumentoBean.getDesTipoDocumento());
			result.setApplicationCode(tipoDocumentoBean.getCodAplicacion());
			result.setWhConceptCode(tipoDocumentoBean.getCodConAlm());
		} catch (TipoDocumentoNotFoundException | TipoDocumentoException e) {
			throw new RuntimeException(e);
		}

		result.setCustomerCode(source.getCabecera().getCliente().getCodCliente());
		result.setRateCode(source.getCabecera().getCliente().getCodtar());

		result.setTaxGroupId(source.getCabecera().getCliente().getIdGrupoImpuestos());
		result.setTaxTreatmentId(source.getCabecera().getCliente().getIdTratImpuestos());
		result.setBaseAmount(source.getCabecera().getTotales().getBase());
		result.setTaxAmount(source.getCabecera().getTotales().getImpuestos());
		result.setGrandAmount(source.getCabecera().getTotales().getTotal());

		result.setUserCode(source.getCabecera().getCajero().getUsuario());

		if(source.getCabecera().getDatosFidelizado() != null) {
			result.setLyCustomerCard(source.getCabecera().getDatosFidelizado().getNumTarjetaFidelizado());
		}
		

		if (source.getCabecera().getDatosDocOrigen() != null) {
			result.setSourceSalesDocCode(source.getCabecera().getDatosDocOrigen().getCodTipoDoc());
			result.setSourceDocTypeId(source.getCabecera().getDatosDocOrigen().getIdTipoDoc());
		}

		result.setCustomerDes(source.getCabecera().getCliente().getDesCliente());
		result.setAddress(source.getCabecera().getCliente().getDomicilio());
		result.setCity(source.getCabecera().getCliente().getPoblacion());
		result.setProvince(source.getCabecera().getCliente().getProvincia());
		result.setLocation(source.getCabecera().getCliente().getLocalidad());
		result.setPostalCode(source.getCabecera().getCliente().getCp());
		result.setIdentificationTypeCode(source.getCabecera().getCliente().getTipoIdentificacion());
		result.setCountryCode(source.getCabecera().getCliente().getCodpais());
		result.setVatNumber(source.getCabecera().getCliente().getCif());
		result.setInvoiceCustomerCode(source.getCabecera().getCliente().getCodCliente());
		result.setCustomerReference(source.getCabecera().getLocalizador());

		result.setDeposit(false);
		result.setPricesWithTaxes(true);

		convertTaxes(datosSesion, source, result);
		convertPayments(datosSesion, source, result);
		convertLines(datosSesion, source, result);

		return result;
	}

	protected void convertTaxes(IDatosSesion datosSesion, final T source, SaleDocHdrDTO destination) {
		List<SaleDocTaxDTO> destinationTaxes = new ArrayList<SaleDocTaxDTO>();

		for (SubtotalIvaTicket sourceSaleDocTax : source.getCabecera().getSubtotalesIva()) {
			SaleDocTaxDTO destinationTax = new SaleDocTaxDTO();
			destinationTax.setTaxTypeCode(sourceSaleDocTax.getCodImpuesto());
			destinationTax.setPercentage(sourceSaleDocTax.getCuota());
			destinationTax.setSurchargePercentage(sourceSaleDocTax.getCuotaRecargo());

			destinationTax.setBaseAmount(sourceSaleDocTax.getBase());
			destinationTax.setTaxAmount(sourceSaleDocTax.getImpuestos());
			destinationTax.setTotal(sourceSaleDocTax.getTotal());

			destinationTaxes.add(destinationTax);
		}

		destination.setTaxes(destinationTaxes);
	}

	protected void convertPayments(IDatosSesion datosSesion, final T source, SaleDocHdrDTO destination) {
		List<SaleDocPaymentDTO> destinationPayments = new ArrayList<SaleDocPaymentDTO>();
		int lineId = 1;

		if (source.getEntregasCuenta() != null && source.getEntregasCuenta().getEntregaCuenta() != null) {
			for (EntregaCuentaTicket sourceSaleDocPayment : source.getEntregasCuenta().getEntregaCuenta()) {
				SaleDocPaymentDTO destinationPayment = new SaleDocPaymentDTO();
				destinationPayment.setLineId(lineId++);
				destinationPayment.setPaymentMethodCode(sourceSaleDocPayment.getCodMedioPago());
				destinationPayment.setPaymentMethodDes(sourceSaleDocPayment.getDesMedioPago());
				destinationPayment.setGrossAmount(sourceSaleDocPayment.getImporte());
				destinationPayment.setTransactionNumber(sourceSaleDocPayment.getUidTransaccionDet());

				destinationPayments.add(destinationPayment);
			}
		}

		for (PagoTicket sourceSaleDocPayment : source.getPagos()) {
			SaleDocPaymentDTO destinationPayment = new SaleDocPaymentDTO();
			destinationPayment.setLineId(lineId++);
			destinationPayment.setPaymentMethodCode(sourceSaleDocPayment.getCodMedioPago());
			destinationPayment.setPaymentMethodDes(sourceSaleDocPayment.getDesMedioPago());
			destinationPayment.setGrossAmount(sourceSaleDocPayment.getImporte());

			destinationPayments.add(destinationPayment);
		}

		destination.setPayments(destinationPayments);
	}

	protected void convertLines(IDatosSesion datosSesion, final T source, SaleDocHdrDTO destination) {
		List<SaleDocLineDTO> destinationLines = new ArrayList<SaleDocLineDTO>();

		for (LineaTicket sourceLine : source.getLineas()) {
			SaleDocLineDTO destinationLine = new SaleDocLineDTO();

			destinationLine.setLineId(sourceLine.getIdLinea());

			destinationLine.setItemCode(sourceLine.getCodArticulo());
			destinationLine.setCombination1Code(sourceLine.getDesglose1());
			destinationLine.setCombination2Code(sourceLine.getDesglose2());
			destinationLine.setItemDes(sourceLine.getDesArticulo());
			destinationLine.setQuantity(sourceLine.getCantidad());
			destinationLine.setSalesPrice(sourceLine.getPrecioConDto());
			destinationLine.setSalesPriceWithTaxes(sourceLine.getPrecioTotalConDto());
			destinationLine.setDiscount(sourceLine.getDescuento());
			destinationLine.setGrossAmount(sourceLine.getImporteConDto());
			destinationLine.setGrandAmount(sourceLine.getImporteTotalConDto());
			destinationLine.setTaxTypeCode(sourceLine.getCodImpuesto());
			destinationLine.setLineType("N");
			destinationLine.setAuxCashier(sourceLine.getVendedor().getUsuario());

			if (sourceLine.getDocumentoOrigen() != null) {
				destinationLine.setOrderLineId(sourceLine.getDocumentoOrigen().getIdLineaDocumentoOrigen());
			}

			convertDiscounts(datosSesion, sourceLine, destinationLine);

			destinationLines.add(destinationLine);
		}

		destination.setLines(destinationLines);
	}

	protected void convertDiscounts(IDatosSesion datosSesion, final LineaTicket sourceLine,
			SaleDocLineDTO destinationLine) {
		List<SaleDocLinePriceModDTO> destinationPriceMods = new ArrayList<SaleDocLinePriceModDTO>();

		// line user Id
		Long lineUserId = null;

		if (sourceLine.getVendedor() != null && StringUtils.isNotEmpty(sourceLine.getVendedor().getUsuario())) {
			try {
				UsuarioBean user = userService.consultar(datosSesion, sourceLine.getVendedor().getUsuario());
				lineUserId = user.getIdUsuario();
			} catch (UsuarioException | UsuarioNotFoundException ignore) {
			}
		}

		// manual price modification
		if (sourceLine.getPrecioTotalSinDto().compareTo(sourceLine.getPrecioTotalTarifaOrigen()) != 0) {
			SaleDocLinePriceModDTO destinationPriceMod = new SaleDocLinePriceModDTO();

			destinationPriceMod.setAppliedTo(APPLIED_IN_LINE);
			destinationPriceMod.setReasonCode(REASON_CODE_MANUAL_PRICE);
			destinationPriceMod.setPriceModificationAmount(sourceLine.getPrecioTotalSinDto()
					.subtract(sourceLine.getPrecioTotalTarifaOrigen()).setScale(2, BigDecimal.ROUND_HALF_UP));

			destinationPriceMods.add(destinationPriceMod);
		}

		// manual discount
		if (sourceLine.getDescuentoManual().compareTo(BigDecimal.ZERO) > 0) {
			SaleDocLinePriceModDTO destinationPriceMod = new SaleDocLinePriceModDTO();

			destinationPriceMod.setAppliedTo(APPLIED_IN_LINE);
			destinationPriceMod.setReasonCode(REASON_CODE_MANUAL_DISCOUNT);
			destinationPriceMod.setPriceModificationAmount(sourceLine.getPrecioTotalConDto()
					.subtract(sourceLine.getPrecioTotalSinDto()).setScale(2, BigDecimal.ROUND_HALF_UP));

			destinationPriceMods.add(destinationPriceMod);
		}

		// promotions
		if (sourceLine.getPromociones() != null && !sourceLine.getPromociones().isEmpty()) {
			for (PromocionLineaTicket sourcePriceMod : sourceLine.getPromociones()) {
				convertPromotionDiscount(sourcePriceMod, destinationPriceMods);
			}
		}

		// promotions from source document
		if (sourceLine.getDocumentoOrigen() != null && sourceLine.getDocumentoOrigen().getPromociones() != null
				&& !sourceLine.getDocumentoOrigen().getPromociones().isEmpty()) {

			for (PromocionLineaTicket sourcePriceMod : sourceLine.getDocumentoOrigen().getPromociones()) {
				convertPromotionDiscount(sourcePriceMod, destinationPriceMods);
			}
		}

		// recalculate input & output price
		int updateLineId = 0;
		BigDecimal inputPrice = sourceLine.getPrecioTotalTarifaOrigen();
		for (SaleDocLinePriceModDTO linePriceModDTO : destinationPriceMods) {
			updateLineId++;
			linePriceModDTO.setUpdateLineId(updateLineId);
			linePriceModDTO.setUserId(lineUserId);
			linePriceModDTO.setInputPrice(inputPrice);
			linePriceModDTO.setOutputPrice(inputPrice.add(linePriceModDTO.getPriceModificationAmount()));

			inputPrice = linePriceModDTO.getOutputPrice();
		}

		// assign price modifications list to line
		destinationLine.setPriceModifications(destinationPriceMods);
	}

	protected void convertPromotionDiscount(final PromocionLineaTicket sourcePriceMod,
			List<SaleDocLinePriceModDTO> destinationPriceMods) {
		SaleDocLinePriceModDTO destinationPriceMod = new SaleDocLinePriceModDTO();

		if (sourcePriceMod.getIdTipoPromocion().compareTo(7L) == 0) {
			destinationPriceMod.setAppliedTo(APPLIED_IN_HEADER);
		} else {
			destinationPriceMod.setAppliedTo(APPLIED_IN_LINE);
		}

		BigDecimal importeDescuento = BigDecimal.ZERO;

		if (sourcePriceMod.getTipoDescuento().compareTo(0L) == 0) { // menos margen
			importeDescuento = sourcePriceMod.getImporteTotalDtoMenosMargen().negate();
		} else { // menos ingreso
			importeDescuento = sourcePriceMod.getImporteTotalDtoMenosIngreso().negate();
			destinationPriceMod.setAppliedTo(APPLIED_IN_PAYMENT);
		}

		destinationPriceMod.setReasonCode(REASON_CODE_PROMOTION);
		destinationPriceMod.setReasonDoc(sourcePriceMod.getIdPromocion().toString());
		destinationPriceMod.setPriceModificationAmount(importeDescuento);

		destinationPriceMods.add(destinationPriceMod);

		// coupon reference
		if (StringUtils.equals(sourcePriceMod.getAcceso(), REASON_CODE_COUPON)) {
			SaleDocLinePriceModDTO couponPriceMod = new SaleDocLinePriceModDTO();
			couponPriceMod.setReasonCode(sourcePriceMod.getAcceso());
			couponPriceMod.setReasonDoc(sourcePriceMod.getCodAcceso());
			couponPriceMod.setPriceModificationAmount(BigDecimal.ZERO);
			couponPriceMod.setAppliedTo(destinationPriceMod.getAppliedTo());
			destinationPriceMods.add(couponPriceMod);
		}
	}

}
