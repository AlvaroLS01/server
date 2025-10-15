package com.comerzzia.omnichannel.service.salesdocument.converters;

import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.exception.BadRequestException;
import com.comerzzia.core.model.tiposdocumentos.TipoDocumentoBean;
import com.comerzzia.core.servicios.empresas.EmpresasService;
import com.comerzzia.core.servicios.sesion.IDatosSesion;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoException;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoNotFoundException;
import com.comerzzia.core.servicios.tipodocumento.TiposDocumentosService;
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
import com.comerzzia.omnichannel.model.documents.sales.generic.DesgloseImpuestoModel;
import com.comerzzia.omnichannel.model.documents.sales.generic.LineaDocumentoVentaModel;
import com.comerzzia.omnichannel.model.documents.sales.generic.PagoDocumentoVentaModel;
import com.comerzzia.omnichannel.model.documents.sales.generic.PromocionLineaModel;
import com.comerzzia.omnichannel.model.documents.sales.order.PedidoModel;
import com.comerzzia.omnichannel.service.documentprint.DocumentPrintService;
import com.comerzzia.omnichannel.service.item.ItemService;
import com.comerzzia.omnichannel.service.salesdocument.metadata.DocumentMetadata;
import com.comerzzia.omnichannel.service.salesdocument.metadata.DocumentMetadataParser;
import com.comerzzia.pos.persistence.paises.PaisMapper;

public abstract class AbstractPedidoModelConverter<T extends PedidoModel> implements SaleDocumentConverter<PedidoModel> {
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
	VariablesService variablesService;
	
	@Autowired
	EmpresasService empresasService;
	
	@Autowired
	DocumentMetadataParser documentMetadataParser;
	
	@Autowired
	ItemService itemService;
		
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired 
	DocumentPrintService documentPrintService;
	
	@Autowired
    protected PaisMapper paisMapper;
	
	@Autowired
	protected ItemDTOConverter itemDTOConverter;

	@Override
	public SaleDocHdrDTO convert(final IDatosSesion datosSesion, final DocumentEntity document) {
		T source = convert(document.getDocumentContent());

		SaleDocHdrDTO result = convertHdr(datosSesion, source);
		
		result.setSalesDocUid(document.getDocumentUid());
		result.setSalesDocNumber(document.getDocumentNumber());
		result.setSalesDocSerial(document.getDocumentSerial());		
		result.setSalesDocCode(document.getDocumentCode());
		
		return result;
	}
	
	@Override
	public DocumentMetadata getMetadata(IDatosSesion datosSesion, DocumentEntity document) {
		return documentMetadataParser.getMetadata(datosSesion, document);
	}

	@Override
	public void printDocument(OutputStream outputStream, IDatosSesion datosSesion, DocumentEntity document,
			PrintDocumentDTO printRequest) throws ApiException {
		// Prepare document data for print 
		prepareDocumentForPrint(datosSesion, document, printRequest);
				
		// Print
		documentPrintService.printToStream(outputStream, datosSesion, printRequest);		
	}
	
	protected void checkMimeType(IDatosSesion datosSesion, DocumentEntity document, PrintDocumentDTO printRequest) throws ApiException {
		if (!(StringUtils.equals(printRequest.getMimeType(), "application/pdf") ||
		 	  StringUtils.equals(printRequest.getMimeType(), "application/jasperprint"))) {
			throw new BadRequestException("Unsupported mime type '" + printRequest.getMimeType() + "' for this document type");
		}		
	}
	
	protected void prepareDocumentForPrint(IDatosSesion datosSesion, DocumentEntity document, PrintDocumentDTO printRequest) throws ApiException {
		if (StringUtils.isBlank(printRequest.getMimeType())) {
			printRequest.setMimeType("application/pdf");
        }
		
		checkMimeType(datosSesion, document, printRequest);
		
		// Create document object
		DocumentMetadata documentMetadata = getMetadata(datosSesion, document);
		PedidoModel documentObject = convert(document.getDocumentContent());
		
		// update lines items auxiliary data
        for (LineaDocumentoVentaModel linea : ((PedidoModel) documentObject).getLineas()) {
           try {
               ItemDTO articulo = itemService.selectDTOByPrimaryKey(datosSesion.getUidActividad(), linea.getCodArticulo());
                                            
               linea.setArticulo(itemDTOConverter.convertToLineaDocumentoVentaArticulo(datosSesion, articulo));
           } catch (Exception ignore) {
           }
        }
        
        // sort lines		
		Collections.sort(documentObject.getLineas(), pedidoModelCategorySorter());
		        
        // document params
		printRequest.getCustomParams().put("PEDIDO_MODEL", documentObject);
		printRequest.getCustomParams().put("document", documentObject);
		printRequest.getCustomParams().put(DocumentPrintService.PARAM_COMPANY_CODE, documentObject.getCabecera().getEmpresa().getCodEmp());
		printRequest.getCustomParams().put(DocumentPrintService.PARAM_LOCALE, documentMetadata.getLocale());
		
		// print template
		if (StringUtils.isEmpty(printRequest.getPrintTemplate())) {
			printRequest.setPrintTemplate(documentMetadata.getPrintTemplate());			
		}
		
		// default template
 		if (StringUtils.isEmpty((String)printRequest.getCustomParams().get(DocumentPrintService.PARAM_DEFAULT_TEMPLATE))) {
 			printRequest.getCustomParams().put(DocumentPrintService.PARAM_DEFAULT_TEMPLATE, getDefaultTemplate(documentMetadata, printRequest.getMimeType()));
 		}
	}
		
	protected String getDefaultTemplate(DocumentMetadata metadata, String outputMimeType) {
		return metadata.getDocTypeCode();
	}
	
	protected Comparator<LineaDocumentoVentaModel> pedidoModelCategorySorter() {
		Comparator<LineaDocumentoVentaModel> comp = new Comparator<LineaDocumentoVentaModel>() {

			@Override
			public int compare(LineaDocumentoVentaModel o1, LineaDocumentoVentaModel o2) {
				int res = 0;
				if (o2.getArticulo() == null && o1.getArticulo() != null) {
					res = -1;
				} else if (o1.getArticulo() == null && o2.getArticulo() != null) {
					res = 1;
				} else if (o2.getArticulo() != null && o1.getArticulo() != null) {
					if (o2.getArticulo().getCodCategorizacion() == null
							&& o1.getArticulo().getCodCategorizacion() != null) {
						res = -1;
					} else if (o1.getArticulo().getCodCategorizacion() == null
							&& o2.getArticulo().getCodCategorizacion() != null) {
						res = 1;
					} else if (o2.getArticulo().getCodCategorizacion() != null
							&& o1.getArticulo().getCodCategorizacion() != null) {
						res = o1.getArticulo().getCodCategorizacion()
								.compareTo(o2.getArticulo().getCodCategorizacion());
					}
				}
				return res;
			}

		};
		return comp;
	}
		
	@SuppressWarnings("unchecked")
	public T convert(final byte[] content) {				
		Class<T> genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractPedidoModelConverter.class);
		
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(genericType);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			return (T) jaxbUnmarshaller.unmarshal(new StringReader(new String(content, "UTF-8")));
		} catch (JAXBException | UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	protected SaleDocHdrDTO convertHdr(IDatosSesion datosSesion, final T source) {
		SaleDocHdrDTO result = new SaleDocHdrDTO();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(source.getCabecera().getFecha());
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
				
		result.setActivityUid(source.getCabecera().getUidActividad());
		result.setCompanyCode(source.getCabecera().getEmpresa().getCodEmp());
		
		result.setPeriod(calendar.get(Calendar.YEAR));
		result.setSerialCode(source.getCabecera().getTienda().getCodAlmacen());
		result.setSalesDocDate(DateUtils.truncate(source.getCabecera().getFecha(), Calendar.DAY_OF_MONTH));
		result.setHour(sdf.format(source.getCabecera().getFecha()));
		result.setSupplyDate(result.getSalesDocDate());
				
		result.setStoreCode(source.getCabecera().getTienda().getCodAlmacen());
		result.setTillCode(source.getCabecera().getCodCaja());		
		result.setDocTypeId(source.getIdTipoDocumento());

		try {
			TipoDocumentoBean tipoDocumentoBean = tiposDocumentosService.consultar(datosSesion, source.getIdTipoDocumento());
			result.setDocTypeCode(tipoDocumentoBean.getCodTipoDocumento());
			result.setDocTypeDes(tipoDocumentoBean.getDesTipoDocumento());
			result.setApplicationCode(tipoDocumentoBean.getCodAplicacion());
			result.setWhConceptCode(tipoDocumentoBean.getCodConAlm());
		} catch (TipoDocumentoNotFoundException | TipoDocumentoException e) {
			throw new RuntimeException(e);
		}		


		result.setCustomerCode(source.getCabecera().getCliente().getCodCliente());
		result.setRateCode(source.getCabecera().getCliente().getCodtar());
					
		//result.setTaxGroupId(source.getCabecera().getCliente().getIdGrupoImpuestos());
		result.setTaxTreatmentId(source.getCabecera().getCliente().getIdTratImpuestos());
		result.setBaseAmount(source.getCabecera().getTotales().getBase());
		result.setTaxAmount(source.getCabecera().getTotales().getImpuestos());
		result.setGrandAmount(source.getCabecera().getTotales().getTotal());

		result.setLyCustomerCard(source.getCabecera().getFidelizado().getNumTarjetaFidelizado());

		if (source.getIdDocumentoOrigen() != null) {
		   result.setSourceSalesDocCode(source.getIdDocumentoOrigen());
		   //result.setSourceDocTypeId(source.getCabecera().getDatosDocOrigen().getIdTipoDoc());
		}

		result.setCustomerDes(source.getCabecera().getCliente().getDesCliente());
		result.setAddress(source.getCabecera().getCliente().getDomicilio());
		result.setCity(source.getCabecera().getCliente().getPoblacion());
		result.setProvince(source.getCabecera().getCliente().getProvincia());
		result.setLocation(source.getCabecera().getCliente().getLocalidad());
		result.setPostalCode(source.getCabecera().getCliente().getCp());
		result.setCountryCode(source.getCabecera().getCliente().getCodpais());
		result.setVatNumber(source.getCabecera().getCliente().getCif());
		result.setInvoiceCustomerCode(source.getCabecera().getCliente().getCodCliente());				
		
		result.setDeposit(false);		
		result.setPricesWithTaxes(source.getCabecera().getPreciosIncluyenImpuestos());
		
		convertTaxes(datosSesion, source, result);
		convertPayments(datosSesion, source, result);
		convertLines(datosSesion, source, result);

		return result;
	}
	
	protected void convertTaxes(IDatosSesion datosSesion, final T source, SaleDocHdrDTO destination) {
		List<SaleDocTaxDTO> destinationTaxes = new ArrayList<SaleDocTaxDTO>();
		
		for (DesgloseImpuestoModel sourceSaleDocTax : source.getCabecera().getImpuestos()) {
			SaleDocTaxDTO destinationTax = new SaleDocTaxDTO();
			destinationTax.setTaxTypeCode(sourceSaleDocTax.getCodImpuesto());
			destinationTax.setPercentage(sourceSaleDocTax.getCuota());
			destinationTax.setSurchargePercentage(sourceSaleDocTax.getRecargo());
			
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
		
		if (source.getPagos() != null && source.getPagos().getPagos() != null) {
			for (PagoDocumentoVentaModel sourceSaleDocPayment : source.getPagos().getPagos()) {
				SaleDocPaymentDTO destinationPayment = new SaleDocPaymentDTO();
				destinationPayment.setLineId(lineId++);
				destinationPayment.setPaymentMethodCode(sourceSaleDocPayment.getMedioPago().getCodMedioPago());
				destinationPayment.setPaymentMethodDes(sourceSaleDocPayment.getMedioPago().getDesMedioPago());
				destinationPayment.setGrossAmount(sourceSaleDocPayment.getImporte());
				destinationPayment.setTransactionNumber(sourceSaleDocPayment.getUidTransaccionDet());
				
				destinationPayments.add(destinationPayment);
			}
		}
				
		destination.setPayments(destinationPayments);
	}
	

	protected void convertLines(IDatosSesion datosSesion, final T source, SaleDocHdrDTO destination) {
		List<SaleDocLineDTO> destinationLines = new ArrayList<SaleDocLineDTO>();

		for (LineaDocumentoVentaModel sourceLine : source.getLineas()) {
			SaleDocLineDTO destinationLine = new SaleDocLineDTO();

			destinationLine.setLineId(sourceLine.getIdLinea());

			destinationLine.setItemCode(sourceLine.getCodArticulo());
			destinationLine.setCombination1Code(sourceLine.getDesglose1());
			destinationLine.setCombination2Code(sourceLine.getDesglose2());
			destinationLine.setItemDes(sourceLine.getDesArticulo());
			destinationLine.setQuantity(sourceLine.getCantidad());
			destinationLine.setSalesPrice(sourceLine.getPrecioSinImpuestos());
			destinationLine.setSalesPriceWithTaxes(sourceLine.getPrecioConImpuestos());
			destinationLine.setDiscount(sourceLine.getDescuentoUnitario());
			destinationLine.setGrossAmount(sourceLine.getImporteSinImpuestos());
			destinationLine.setGrandAmount(sourceLine.getImporteConImpuestos());
			destinationLine.setTaxTypeCode(sourceLine.getCodImpuesto());
			destinationLine.setLineType("N");			
			
			convertDiscounts(datosSesion, sourceLine, destinationLine);

			destinationLines.add(destinationLine);
		}

		destination.setLines(destinationLines);
	}

	protected void convertDiscounts(IDatosSesion datosSesion, final LineaDocumentoVentaModel sourceLine, SaleDocLineDTO destinationLine) {
		List<SaleDocLinePriceModDTO> destinationPriceMods = new ArrayList<SaleDocLinePriceModDTO>();
						
		// promotions
		if (sourceLine.getPromocionesAplicadas() != null && !sourceLine.getPromocionesAplicadas().isEmpty()) {
			for (PromocionLineaModel sourcePriceMod : sourceLine.getPromocionesAplicadas()) {
				convertPromotionDiscount(sourcePriceMod, destinationPriceMods);				
			}
		}
				
		// recalculate input & output price
		int updateLineId = 0;
		BigDecimal inputPrice = sourceLine.getPrecioTarifa();
		
		for (SaleDocLinePriceModDTO linePriceModDTO : destinationPriceMods) {			
			updateLineId++;
			linePriceModDTO.setUpdateLineId(updateLineId);
			linePriceModDTO.setInputPrice(inputPrice);
			linePriceModDTO.setOutputPrice(inputPrice.add(linePriceModDTO.getPriceModificationAmount()));
			
			inputPrice = linePriceModDTO.getOutputPrice();
		}

		// assign price modifications list to line
		destinationLine.setPriceModifications(destinationPriceMods);
	}
	
	protected void convertPromotionDiscount(final PromocionLineaModel sourcePriceMod, List<SaleDocLinePriceModDTO> destinationPriceMods) {
		SaleDocLinePriceModDTO destinationPriceMod = new SaleDocLinePriceModDTO();

    	if (sourcePriceMod.getIdTipoPromocion().compareTo(7L) == 0) {
     	   destinationPriceMod.setAppliedTo(APPLIED_IN_HEADER);
     	} else {
     	   destinationPriceMod.setAppliedTo(APPLIED_IN_LINE);
     	}
    	
		BigDecimal importeDescuento = BigDecimal.ZERO;
		
    	if (sourcePriceMod.getTipoDescuento().compareTo(0L) == 0){ // menos margen
    		importeDescuento = sourcePriceMod.getImporteTotalDtoMenosMargen().negate();
    	}
    	else{ // menos ingreso
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
