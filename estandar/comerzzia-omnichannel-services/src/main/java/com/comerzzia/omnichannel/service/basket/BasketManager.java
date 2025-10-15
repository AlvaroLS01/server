package com.comerzzia.omnichannel.service.basket;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.exception.BadRequestException;
import com.comerzzia.api.core.service.exception.NotFoundException;
import com.comerzzia.omnichannel.domain.dto.basket.BasketItemsUpdateEventDTO;
import com.comerzzia.omnichannel.domain.dto.basket.BasketTransactionDTO;
import com.comerzzia.omnichannel.domain.dto.basket.CouponAppliedDTO;
import com.comerzzia.omnichannel.domain.dto.basket.CouponDTO;
import com.comerzzia.omnichannel.domain.dto.basket.CouponIssueDTO;
import com.comerzzia.omnichannel.domain.dto.basket.DiscountSummaryDTO;
import com.comerzzia.omnichannel.domain.dto.basket.LoyaltyCardExceptionDTO;
import com.comerzzia.omnichannel.domain.dto.basket.LoyaltySettingsDTO;
import com.comerzzia.omnichannel.domain.dto.basket.PriceSettingsDTO;
import com.comerzzia.omnichannel.domain.dto.basket.StartTransactionDTO;
import com.comerzzia.omnichannel.domain.dto.basket.TotalsDTO;
import com.comerzzia.omnichannel.domain.dto.basket.TransactionVoidedDTO;
import com.comerzzia.omnichannel.domain.dto.basket.VoidTransactionDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.BasketItemSoldDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.BasketItemVoidedDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.exception.ItemExceptionDTO;
import com.comerzzia.omnichannel.domain.dto.basket.pay.TenderAcceptedDTO;
import com.comerzzia.omnichannel.domain.entity.basket.BasketEntity;
import com.comerzzia.omnichannel.domain.entity.basket.BasketKey;
import com.comerzzia.omnichannel.model.documents.sales.basket.BasketDocument;
import com.comerzzia.omnichannel.model.documents.sales.basket.items.BasketItemSold;
import com.comerzzia.pos.persistence.core.documentos.tipos.TipoDocumentoBean;
import com.comerzzia.pos.persistence.fidelizacion.CustomerCouponDTO;
import com.comerzzia.pos.persistence.fidelizacion.FidelizacionBean;
import com.comerzzia.pos.persistence.mediosPagos.MedioPagoBean;
import com.comerzzia.pos.services.core.documentos.DocumentoException;
import com.comerzzia.pos.services.core.documentos.Documentos;
import com.comerzzia.pos.services.core.sesion.Sesion;
import com.comerzzia.pos.services.core.sesion.SesionPromociones;
import com.comerzzia.pos.services.core.variables.VariablesServices;
import com.comerzzia.pos.services.cupones.CuponAplicationException;
import com.comerzzia.pos.services.cupones.CuponUseException;
import com.comerzzia.pos.services.cupones.CuponesServiceException;
import com.comerzzia.pos.services.cupones.CuponesServices;
import com.comerzzia.pos.services.mediospagos.MediosPagosService;
import com.comerzzia.pos.services.payments.events.PaymentOkEvent;
import com.comerzzia.pos.services.payments.methods.PaymentMethodManager;
import com.comerzzia.pos.services.payments.methods.types.BasicPaymentMethodManager;
import com.comerzzia.pos.services.promociones.DocumentoPromocionable;
import com.comerzzia.pos.services.promociones.Promocion;
import com.comerzzia.pos.services.ticket.ITicket;
import com.comerzzia.pos.services.ticket.Ticket;
import com.comerzzia.pos.services.ticket.TicketVenta;
import com.comerzzia.pos.services.ticket.TicketVentaAbono;
import com.comerzzia.pos.services.ticket.TicketsService;
import com.comerzzia.pos.services.ticket.TicketsServiceException;
import com.comerzzia.pos.services.ticket.cabecera.CabeceraTicket;
import com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket;
import com.comerzzia.pos.services.ticket.cupones.CuponAplicadoTicket;
import com.comerzzia.pos.services.ticket.cupones.CuponEmitidoTicket;
import com.comerzzia.pos.services.ticket.lineas.ILineaTicket;
import com.comerzzia.pos.services.ticket.lineas.LineaTicket;
import com.comerzzia.pos.services.ticket.lineas.LineaTicketAbstract;
import com.comerzzia.pos.services.ticket.pagos.PagoTicket;
import com.comerzzia.pos.services.ticket.pagos.tarjeta.DatosRespuestaPagoTarjeta;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;
import com.comerzzia.pos.util.config.SpringContext;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

@SuppressWarnings("rawtypes")
public class BasketManager {
	protected Logger log = Logger.getLogger(getClass());
	public static final String PLANTILLA_CUPON = "cupon_promocion";

	protected ITicket basketCalculator;

	protected TipoDocumentoBean documentoActivo;

	protected Integer itemsCounter;

	@Autowired
	protected MediosPagosService paymentsMethodsService;

	@Autowired
	protected TicketsService ticketsService;

	@Autowired
	protected VariablesServices variablesServices;

	@Autowired
	protected CuponesServices couponsService;

	@Autowired
	protected BasketService basketService;

	protected BasketTransactionDTO basketTransaction;

	protected ObjectMapper jsonObjectMapper;

	protected final Sesion sesion;
	protected final BasketItemsManager basketItemsManager;
	protected final BasketPaymentsManager basketPaymentsManager;
	
	@Autowired
	ModelMapper modelMapper;

	public BasketManager(Sesion sesion, BasketItemsManager basketItemsManager, BasketPaymentsManager basketPaymentsManager) {
		this.sesion = sesion;
		this.basketItemsManager = basketItemsManager;
		this.basketItemsManager.setBaskeManager(this);
		
		this.basketPaymentsManager = basketPaymentsManager;
		this.basketPaymentsManager.setBaskeManager(this);
		

		jsonObjectMapper = new ObjectMapper();
		// jaxb annotations support
		JaxbAnnotationModule module = new JaxbAnnotationModule();
		jsonObjectMapper.registerModule(module);
		jsonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		jsonObjectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	public final ITicket getBasketCalculator() {
		return basketCalculator;
	}

	public BasketItemsManager getBasketItemsManager() {
		return basketItemsManager;
	}

	public BasketPaymentsManager getBasketPaymentsManager() {
		return basketPaymentsManager;
	}
	
	public Sesion getSesion() {
		return sesion;
	}

	public void initilizeBasket() throws ApiException {
		initilizeBasket(null);
	}

	@SuppressWarnings("unchecked")
	public void initilizeBasket(StartTransactionDTO startTransaction) throws ApiException {
		log.debug("initilizeBasket() - Creating basket with default values...");

		try {
			if (startTransaction != null && startTransaction.getDocumentType() != null) {
				documentoActivo = sesion.getAplicacion().getDocumentos()
						.getDocumento(startTransaction.getDocumentType());

				if (documentoActivo == null) {
					throw new BadRequestException("Document type " + startTransaction.getDocumentType() + " not found.");
				}
			} else {
				documentoActivo = sesion.getAplicacion().getDocumentos().getDocumento(Documentos.FACTURA_SIMPLIFICADA);
			}

			basketCalculator = SpringContext.getBean(getTicketClass(documentoActivo));
			basketCalculator.setEsDevolucion(false);
			basketCalculator.getCabecera().inicializarCabecera(basketCalculator);
			((TicketVentaAbono) basketCalculator).inicializarTotales();
			basketCalculator.setCliente(sesion.getAplicacion().getTienda().getCliente().clone());
			basketCalculator.setCajero(sesion.getSesionUsuario().getUsuario());
			basketCalculator.getCabecera().getTotales()
					.setCambio(SpringContext.getBean(PagoTicket.class, sesion.getSesionCaja().getMedioPagoDefecto()));
			basketCalculator.getTotales().recalcular();
			itemsCounter = 0;

			// Establecemos los parámetros de tipo de documento del ticket
			basketCalculator.getCabecera().setDocumento(documentoActivo);
		} catch (DocumentoException //| PromocionesServiceException 
				e) {
			log.error(e);
			throw new RuntimeException("Internal error detected: " + e.getMessage(), e);
		}

		basketTransaction = new BasketTransactionDTO();
		basketTransaction.setId(basketCalculator.getCabecera().getUidTicket());
		basketTransaction.setDestinationDocumentType(documentoActivo.getCodtipodocumento());
		
		setPriceSettings(startTransaction.getPriceSettings());
		setLoyaltySettings(startTransaction.getLoyaltySettings());		
		
		updateTotals();
	}

	@SuppressWarnings({ "unchecked" })
	protected Class<? extends ITicket> getTicketClass(TipoDocumentoBean tipoDocumento) {
		String claseDocumento = tipoDocumento.getClaseDocumento();
		if (claseDocumento != null) {
			try {
				return (Class<? extends ITicket>) Class.forName(claseDocumento);
			} catch (ClassNotFoundException e) {
				log.error(String.format("getTicketClass() - Clase %s no encontrada, devolveremos TicketVentaAbono",
						claseDocumento));
			}
		}
		return TicketVentaAbono.class;
	}
	

	public BasketTransactionDTO getBasketTransaction() {
		return basketTransaction;
	}
	
	@SuppressWarnings("unchecked")
	protected BasketItemsUpdateEventDTO setBasketTransaction(final BasketDocument sourceBasketDocument) throws ApiException {
		log.debug("setBasketTransaction() - Creating new basket calculator based on basket " + sourceBasketDocument.getId());

		try {
			if (sourceBasketDocument.getDestinationDocumentType() != null) {
				documentoActivo = sesion.getAplicacion().getDocumentos()
						.getDocumento(sourceBasketDocument.getDestinationDocumentType());

				if (documentoActivo == null) {
					throw new BadRequestException("Document type " + sourceBasketDocument.getDestinationDocumentType() + " not found.");
				}
			} else {
				documentoActivo = sesion.getAplicacion().getDocumentos().getDocumento(Documentos.FACTURA_SIMPLIFICADA);
			}

			basketCalculator = SpringContext.getBean(getTicketClass(documentoActivo));
			basketCalculator.setEsDevolucion(false);
			basketCalculator.getCabecera().inicializarCabecera(basketCalculator);
			((TicketVentaAbono) basketCalculator).inicializarTotales();
			basketCalculator.setCliente(sesion.getAplicacion().getTienda().getCliente().clone());
			basketCalculator.setCajero(sesion.getSesionUsuario().getUsuario());
			basketCalculator.getCabecera().getTotales()
					.setCambio(SpringContext.getBean(PagoTicket.class, sesion.getSesionCaja().getMedioPagoDefecto()));
			basketCalculator.getTotales().recalcular();
			itemsCounter = 0;

			// Establecemos los parámetros de tipo de documento del ticket
			basketCalculator.getCabecera().setDocumento(documentoActivo);
		} catch (DocumentoException e) {
			log.error(e);
			throw new RuntimeException("Internal error detected: " + e.getMessage(), e);
		}

		// maintain original id
		basketCalculator.getCabecera().setUidTicket(sourceBasketDocument.getId());
		
		basketTransaction = new BasketTransactionDTO();
		
		// copy values from source basket
		basketTransaction.setId(sourceBasketDocument.getId());
		basketTransaction.setVersion(sourceBasketDocument.getVersion());
		basketTransaction.setDestinationDocumentType(sourceBasketDocument.getDestinationDocumentType());
		basketTransaction.setCreationDate(sourceBasketDocument.getCreationDate());
		basketTransaction.setLastUpdate(sourceBasketDocument.getLastUpdate());
		basketTransaction.setTrainingMode(sourceBasketDocument.getTrainingMode());
		basketTransaction.setTenderMode(sourceBasketDocument.getTenderMode());
		basketTransaction.setScoMode(sourceBasketDocument.getScoMode());
		basketTransaction.setTillTransactionId(sourceBasketDocument.getTillTransactionId());
		basketTransaction.setCurrentTillTransactionDetailId(sourceBasketDocument.getCurrentTillTransactionDetailId());
		if (sourceBasketDocument.getPayments() != null) {
			basketTransaction.setPayments(modelMapper.map(sourceBasketDocument.getPayments(), new TypeToken<HashMap<Integer, TenderAcceptedDTO>>() {
		      }.getType()));
		}
		
		if (sourceBasketDocument.getPriceSettings() != null) {
		   basketTransaction.setPriceSettings(modelMapper.map(sourceBasketDocument.getPriceSettings(), PriceSettingsDTO.class));
		}
		if (sourceBasketDocument.getLoyaltySettings() != null) {
		   basketTransaction.setLoyaltySettings(modelMapper.map(sourceBasketDocument.getLoyaltySettings(), LoyaltySettingsDTO.class));
		}

		BasketItemsUpdateEventDTO itemsUpdateEvent = new BasketItemsUpdateEventDTO(basketTransaction);
		
		try {		
			if (sourceBasketDocument.getLoyaltyCard() != null) {
				basketItemsManager.loyaltyCard(sourceBasketDocument.getLoyaltyCard().getAccountNumber());
			}
		} catch (LoyaltyCardExceptionDTO e) {
			log.error("Error loading loyalty card from basket: " + e.getMessage(), e);
			
			itemsUpdateEvent.setLoyaltyCardException(e);
		}
		
		for (Map.Entry<Integer, BasketItemSold> item : sourceBasketDocument.getItems().entrySet()) {
           try {
        	   basketItemsManager.newItemSale(modelMapper.map(item.getValue(), BasketItemSoldDTO.class));
			} catch (ItemExceptionDTO e) {
				log.error("Error loading item sold " + item.getKey() + ": " + e.getMessage(), e);
				
				// set last item exception
				itemsUpdateEvent.setItemException(e);
				
				BasketItemVoidedDTO itemVoided = new BasketItemVoidedDTO();
				itemVoided.setItemNumber(item.getKey());
				itemVoided.setUpc(item.getValue().getUpc());
				
				itemsUpdateEvent.addItemVoided(itemVoided);				
			}
		}
		
		recalculateBasket();
		basketItemsManager.updateItemsSoldDiscounts();
		
		// retrieve current payments
		if (sourceBasketDocument.getPayments() != null && sourceBasketDocument.getPayments().size() > 0) {
			sourceBasketDocument.getPayments().forEach((key, value) -> {
				PagoTicket pago = SpringContext.getBean(PagoTicket.class);
				pago.setPaymentId(key);
				pago.setCodMedioPago(value.getPaymentMethodCode());
				pago.setDesMedioPago(value.getDescription());
				pago.setImporte(value.getAmount());
				pago.setEliminable(value.getCancellable());
				pago.setIntroducidoPorCajero(value.getEnterByCashier());
				pago.setUidTransaccionDet(value.getTillTransactionDetailId());
				
				basketCalculator.getPagos().add(pago);
			});
			
		}		
		
		updateTotals();
		
		log.debug("setBasketTransaction() - Current basket calculator " + getBasketTransaction().getId() + " / " + getBasketCalculator().getUidTicket());
		
		return itemsUpdateEvent;
	}
	
	public void loadBasket(String id) throws ApiException {
		BasketKey key = new BasketKey();
		key.setUidActividad(sesion.getUidActividad());
		key.setUidCesta(id);
		
		BasketEntity basketRecord = basketService.selectByPrimaryKey(sesion, key);
		
		if (basketRecord == null) {
			throw new NotFoundException();
		}
		
		setBasketTransaction(createDataObject(basketRecord.getCesta()));
	}	
	

	public void deleteItem(Integer idLinea) {
		log.debug("deleteItem() - Eliminando línea de ticket con idLinea: " + idLinea);
		ILineaTicket linea = ((TicketVentaAbono) basketCalculator).getLinea(idLinea);
		basketCalculator.getLineas().remove(linea);
		basketTransaction.getItems().remove(idLinea);
		recalculateBasket();
	}

	@SuppressWarnings("unchecked")
	public void recalculateBasket() {
		log.debug("recalculateBasket()");
		basketCalculator.getTotales().recalcular();
		sesion.getSesionPromociones().aplicarPromociones((DocumentoPromocionable<IPromocionTicket>) basketCalculator);
		basketCalculator.getTotales().recalcular();
		
		updateTotals();
	}


	public LineaTicket addLineToBasketCalculator(LineaTicketAbstract linea) {
		log.debug("addLineToBasketCalculator() - Insert new line into ticket...");

		itemsCounter++;

		linea.setIdLinea(itemsCounter);

		((TicketVentaAbono) basketCalculator).addLinea((LineaTicket) linea);

		return (LineaTicket) linea;

	}
	
	public void updateBasketCalculatorLine(int itemNumber, LineaTicketAbstract linea) {
		log.debug("updateBasketCalculatorLine() - Update item ...");
		
//		LineaTicket lineaOriginal = ((TicketVentaAbono) basketCalculator).getLinea(itemNumber);
//		
//        lineaOriginal.setPrecioSinDto(linea.getPrecioSinDto());
//        lineaOriginal.setPrecioTotalSinDto(linea.getPrecioTotalSinDto());
//        lineaOriginal.setDescuentoManual(linea.getDescuentoManual());        
//        lineaOriginal.setCantidad(linea.getCantidad());
//        lineaOriginal.setVendedor(linea.getVendedor());
//        lineaOriginal.setDesArticulo(linea.getDesArticulo());
//        lineaOriginal.setNumerosSerie(((LineaTicket)linea).getNumerosSerie());
//        lineaOriginal.recalcularImporteFinal();

		recalculateBasket();

//		return (LineaTicket) lineaOriginal;
	}
	

	@SuppressWarnings("unchecked")
	public TenderAcceptedDTO addPayToBasket(PaymentOkEvent eventOk, boolean enterByCashier) {
		log.debug("addPayToBasket() - Insert pay to basket...");

		BigDecimal amount = eventOk.getAmount();
		String paymentCode = ((PaymentMethodManager) eventOk.getSource()).getPaymentCode();
		Integer paymentId = eventOk.getPaymentId();
		boolean cashFlowRecorded = ((PaymentMethodManager) eventOk.getSource()).recordCashFlowImmediately();
		MedioPagoBean paymentMethod = paymentsMethodsService.getMedioPago(paymentCode);

		PagoTicket pago = SpringContext.getBean(PagoTicket.class, paymentMethod);

		pago.setEliminable(false);
		pago.setImporte(amount);
		pago.setIntroducidoPorCajero(enterByCashier);
		pago.setMovimientoCajaInsertado(cashFlowRecorded);

		if (paymentId != null) {
			pago.setPaymentId(paymentId);
		}

		if (paymentMethod.getTarjetaCredito() != null && paymentMethod.getTarjetaCredito()) {
			if (eventOk.getExtendedData().containsKey(BasicPaymentMethodManager.PARAM_RESPONSE_TEF)) {
				DatosRespuestaPagoTarjeta datosRespuestaPagoTarjeta = (DatosRespuestaPagoTarjeta) eventOk
						.getExtendedData().get(BasicPaymentMethodManager.PARAM_RESPONSE_TEF);
				pago.setDatosRespuestaPagoTarjeta(datosRespuestaPagoTarjeta);
			}
		}

		((TicketVenta) basketCalculator).addPago(pago);

		basketCalculator.getTotales().recalcular();

		// Pay accepted message
		TenderAcceptedDTO response = new TenderAcceptedDTO();
		response.setPaymentId(paymentId);
		response.setPaymentMethodCode(paymentCode);
		response.setDescription(paymentMethod.getDesMedioPago());
        response.setTillTransactionDetailId(basketTransaction.getCurrentTillTransactionDetailId());

        response.setAmount(amount);

		response.setEnterByCashier(enterByCashier);
		response.setCancellable(cashFlowRecorded);

		// TODO: no implementado en el manejador de medios de pago
		response.setCancellable(true);

		// update basket transaction
		
		// clear current pay transaction
		basketTransaction.setCurrentTillTransactionDetailId(null);
		
		if (basketTransaction.getPayments() == null) {
			basketTransaction.setPayments(new HashMap<Integer, TenderAcceptedDTO>());
		}

		basketTransaction.getPayments().put(paymentId, response);

		updateTotals();
		
		try {
			persistBasket();
		} catch (ApiException e) {
			throw new RuntimeException(e);
		}

		return response;
	}

	@SuppressWarnings("unchecked")
	public void saveSalesDocument() {
		sesion.getSesionPromociones().aplicarPromocionesFinales((DocumentoPromocionable<IPromocionTicket>) basketCalculator);

		// Registrar cupones utilizados. Si hay algún error, se ignorará y se
		// registrarán al procesar el ticket en central
		// cuponesService.registraUsoCupones((TicketVentaAbono) ticketPrincipal);

		if (basketCalculator.getIdTicket() == null) {
			try {
				ticketsService.setContadorIdTicket((Ticket) basketCalculator);
			} catch (TicketsServiceException e) {
				throw new RuntimeException("Error asignando contador de ticket", e);
			}
		}
		sesion.getSesionPromociones().generarCuponesDtoFuturo(basketCalculator);

		basketCalculator.getTotales().recalcular();

		if (!isTrainingMode()) {
			try {
				ticketsService.registrarTicket((Ticket) basketCalculator, documentoActivo, false);
			} catch (TicketsServiceException e) {
				throw new RuntimeException("Error salvando ticket", e);
			}
		}
	}
	
	


	@SuppressWarnings("unchecked")
	public void updateTotals() {
		// calculate final promotions (points)
		sesion.getSesionPromociones().aplicarPromocionesFinales((DocumentoPromocionable<IPromocionTicket>) basketCalculator);
		sesion.getSesionPromociones().generarCuponesDtoFuturo(basketCalculator);
		basketCalculator.getTotales().recalcular();
		
		ITotalesTicket totales = basketCalculator.getTotales();

		BigDecimal total = totales.getTotalAPagar();
		BigDecimal pagado = totales.getEntregado();

		TotalsDTO totals = basketTransaction.getTotals();
		totals.setTotalAmount(totales.getTotalAPagar());
		totals.setTaxAmount(totales.getImpuestos());

		// pay control for change
		if (total.compareTo(pagado) >= 0) {
			totals.setBalanceDue(total.subtract(pagado));
		} else {
			totals.setBalanceDue(BigDecimal.ZERO);
			totals.setChangeDue(pagado.subtract(total));

		}
		
		totals.setItemCount(basketCalculator.getLineas().size());
		
		totals.setDiscountAmount(totales.getTotalPromociones());
		totals.setPoints(totales.getPuntos());
		
		// discounts summary
		List<DiscountSummaryDTO> discountsSummary = new ArrayList<DiscountSummaryDTO>();
		
		for (IPromocionTicket promotion : ((List<IPromocionTicket>) basketCalculator.getPromociones())) {
			DiscountSummaryDTO discountSummary = new DiscountSummaryDTO();
			discountSummary.setOrigin(promotion.getAcceso());
			discountSummary.setUpc(promotion.getCodAcceso());
			
			discountSummary.setDiscountType(promotion.getIdTipoPromocion());
			
			discountSummary.setDescription(promotion.getTextoPromocion());
			
			discountSummary.setPointsObtained(promotion.getPuntos());
			discountSummary.setDiscountDestination(promotion.getTipoDescuento());

			discountSummary.setExclusive(promotion.isExclusiva());
			
			discountSummary.setAmount(promotion.getImporteTotalAhorro());			

			discountsSummary.add(discountSummary);			
		}
		
		basketTransaction.setDiscountsSummary(discountsSummary);
		
		// Applied coupons		
		SesionPromociones sesionPromociones = sesion.getSesionPromociones();
		
		HashMap<String, CouponAppliedDTO> couponsApplied = new LinkedHashMap<String, CouponAppliedDTO>();
		
		for (CuponAplicadoTicket appliedCoupon : ((TicketVentaAbono)basketCalculator).getCuponesAplicados()) {
			Promocion promotion = sesionPromociones.getPromocionActiva(appliedCoupon.getIdPromocion());
			
			CouponAppliedDTO coupon = new CouponAppliedDTO();
			coupon.setCouponType(appliedCoupon.getTipoCupon());
			coupon.setUpc(appliedCoupon.getCodigo());
			coupon.setName(appliedCoupon.getTextoPromocion());
			coupon.setDescription(null);
			coupon.setStartDate(null);
			coupon.setEndDate(null);
			coupon.setImageUrl(null);
			
			coupon.setPromotionId(appliedCoupon.getIdPromocion());
			
			if (promotion != null) {
			   coupon.setPromotionTypeId(promotion.getIdTipoPromocion());
			   coupon.setPromotionDes(promotion.getTextoPromocion());
			   
			}
			
			coupon.setDiscountAmount(appliedCoupon.getImporteTotalAhorrado());
			
			couponsApplied.put(coupon.getUpc(), coupon);
		}
		
		basketTransaction.setCouponsApplied(couponsApplied);
		
		// coupons to issue		
		basketTransaction.setCouponsToIssue(getCouponsToIssue());
	}
	
	@SuppressWarnings({"unchecked" })
    protected List<CouponIssueDTO> getCouponsToIssue() {
		List<CouponIssueDTO> couponsToIssue = new ArrayList<>();
		
		for (CuponEmitidoTicket promotionCouponData : (List<CuponEmitidoTicket>)basketCalculator.getCuponesEmitidos()) {
			CouponIssueDTO newCoupon = new CouponIssueDTO();				
			newCoupon.setPromotionId(promotionCouponData.getIdPromocionAplicacion());				
			newCoupon.setStartDate(promotionCouponData.getFechaInicio());
			newCoupon.setEndDate(promotionCouponData.getFechaFin());
			newCoupon.setCouponType(promotionCouponData.getTipoCupon());				
			newCoupon.setAmount(promotionCouponData.getImporteCupon());
			if (promotionCouponData.getMaximoUsos() != null) {
			   newCoupon.setMaxUses(promotionCouponData.getMaximoUsos().longValue());
			}
			newCoupon.setImageUrl(promotionCouponData.getImagenCupon());
	        newCoupon.setName(promotionCouponData.getTituloCupon());
			newCoupon.setDescription(promotionCouponData.getDescripcionCupon());
						
			couponsToIssue.add(newCoupon);
		}
		
		return couponsToIssue;
    }

	public TransactionVoidedDTO voidTransaction(VoidTransactionDTO message) throws ApiException {
		//TODO: Operation permissions, basket status validation to be voided
		BasketEntity key = new BasketEntity();
		key.setUidCesta(basketTransaction.getId());
		basketService.deleteByPrimaryKey(sesion, key);
		
		TransactionVoidedDTO response = new TransactionVoidedDTO();
		response.setMessage("ok");
		return response;
	}

	@SuppressWarnings("unchecked")
	public boolean applyCoupon(CouponDTO coupon) {
		try {
			CustomerCouponDTO customerCoupon = new CustomerCouponDTO(coupon.getUpc(), false);
			customerCoupon.setActive(true);
			customerCoupon.setBalance(coupon.getAmount());
			customerCoupon.setCouponName(coupon.getName());
			customerCoupon.setCouponDescription(coupon.getDescription());
			customerCoupon.setStartDate(coupon.getStartDate());
			customerCoupon.setEndDate(coupon.getEndDate());
			customerCoupon.setPromotionId(coupon.getPromotionId());
			
			return sesion.getSesionPromociones().aplicarCupon(customerCoupon, (DocumentoPromocionable<IPromocionTicket>)basketCalculator);
		} catch (CuponUseException | CuponesServiceException | CuponAplicationException e) {
			return false;
		}
	}

	public boolean isTrainingMode() {
		return this.basketTransaction.getTrainingMode();
	}

	public void setTrainingMode(boolean trainingMode) {
		this.basketTransaction.setTrainingMode(trainingMode);
	}

	protected void persist() throws ApiException {
		boolean isInsert = (basketTransaction.getVersion() == null);

		basketTransaction.setLastUpdate(new Date());
		basketTransaction.setVersion(System.currentTimeMillis());

		BasketEntity basketRecord = new BasketEntity();
		basketRecord.setUidActividad(sesion.getUidActividad());
		basketRecord.setUidCesta(basketTransaction.getId());
		basketRecord.setCodAlmacen(sesion.getAplicacion().getCodAlmacen());
		basketRecord.setCodcaja(sesion.getAplicacion().getCodCaja());
		basketRecord.setCodCliente(basketCalculator.getCabecera().getCliente().getCodCliente());
		basketRecord.setIdTipoDocumento(documentoActivo.getIdTipoDocumento());
		if (basketCalculator.getCabecera().getDatosFidelizado() != null) {
			basketRecord.setIdFidelizado(basketCalculator.getCabecera().getDatosFidelizado().getIdFidelizado());
		}
		basketRecord.setNumArticulos(itemsCounter);
		basketRecord.setImporte(basketCalculator.getTotales().getTotal());
		basketRecord.setUsuario(sesion.getUser());
		basketRecord.setFechaCreacion(basketTransaction.getCreationDate());
		basketRecord.setFechaActualizacion(basketTransaction.getLastUpdate());
		basketRecord.setCesta(serializeDataObject(basketTransaction));

		if (isInsert) {
			basketService.insert(sesion, basketRecord);
		} else {
			basketService.updateByPrimaryKey(sesion, basketRecord);
		}
	}

	private byte[] serializeDataObject(final BasketTransactionDTO data) {
		try {
			return jsonObjectMapper.writeValueAsBytes(modelMapper.map(data, BasketDocument.class));
		} catch (JsonParseException | JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private BasketDocument createDataObject(final byte[] data) {
		if (data == null) {
			return null;
		}

		try {
			return jsonObjectMapper.readValue(new String(data), BasketDocument.class);
		} catch (JsonParseException | JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void persistBasket() throws ApiException {
		persist();
	}
	
	public void setLoyaltySettings(LoyaltySettingsDTO loyaltySettings) {		
		basketTransaction.setLoyaltySettings(loyaltySettings);
		
		FidelizacionBean fidelizado = null;
		
		if (loyaltySettings != null && loyaltySettings.isActive()) {
			fidelizado = new FidelizacionBean();
			
			if (loyaltySettings.getLoyalCustomerId() != null) {
				fidelizado.setIdFidelizado(loyaltySettings.getLoyalCustomerId());
			}
		
			if (loyaltySettings.getCollectives() != null && loyaltySettings.getCollectives().size() > 0) {
				fidelizado.setCodColectivos(loyaltySettings.getCollectives());	
			}
			
			if (loyaltySettings.getTags() != null && loyaltySettings.getTags().size() > 0) {			
			   fidelizado.setUidEtiquetas(loyaltySettings.getTags());
			}
		}
		
		basketCalculator.getCabecera().setDatosFidelizado(fidelizado);
	}
	
	public void setPriceSettings(PriceSettingsDTO priceSettings) {
		if (priceSettings != null && priceSettings.isActive()) {
			((CabeceraTicket)basketCalculator.getCabecera()).setTarifas(new LinkedHashSet<String>(priceSettings.getRates()));
		} else {
			((CabeceraTicket)basketCalculator.getCabecera()).setTarifas(null);
		}
		
		basketTransaction.setPriceSettings(priceSettings);
	}

	public com.comerzzia.omnichannel.model.documents.sales.ticket.TicketVentaAbono getModelo() {
		com.comerzzia.pos.services.ticket.TicketVentaAbono ticket = (com.comerzzia.pos.services.ticket.TicketVentaAbono)basketCalculator;
		
	    return modelMapper.map(ticket, com.comerzzia.omnichannel.model.documents.sales.ticket.TicketVentaAbono.class);
	}
	
	public TipoDocumentoBean getDocumentoActivo() {
		return documentoActivo;
	}

}
