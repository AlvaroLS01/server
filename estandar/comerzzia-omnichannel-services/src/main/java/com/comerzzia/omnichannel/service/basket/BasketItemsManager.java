package com.comerzzia.omnichannel.service.basket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.comerzzia.api.core.service.exception.ApiException;
import com.comerzzia.api.core.service.exception.BadRequestException;
import com.comerzzia.omnichannel.domain.dto.basket.BasketItemsUpdateEventDTO;
import com.comerzzia.omnichannel.domain.dto.basket.LoyaltyCardDTO;
import com.comerzzia.omnichannel.domain.dto.basket.LoyaltyCardExceptionDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.BasketItemDiscountDetailDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.BasketItemDataDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.BasketItemSoldDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.BasketItemVoidedDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.exception.ItemExceptionDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.exception.ItemSalesExceptionDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.request.BasketItemRequestDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.request.BasketItemSaleUpdateRequestDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.request.BasketItemVoidRequestDTO;
import com.comerzzia.omnichannel.domain.dto.basket.items.request.IBasketItemUpdateRequest;
import com.comerzzia.omnichannel.domain.dto.item.ItemDTO;
import com.comerzzia.omnichannel.service.item.ItemService;
import com.comerzzia.pos.persistence.articulos.tarifas.TarifaDetalleBean;
import com.comerzzia.pos.persistence.codBarrasEspeciales.CodigoBarrasEspecialBean;
import com.comerzzia.pos.persistence.core.documentos.tipos.TipoDocumentoBean;
import com.comerzzia.pos.persistence.tarifas.TarifaBean;
import com.comerzzia.pos.services.articulos.tarifas.ArticuloTarifaNotFoundException;
import com.comerzzia.pos.services.articulos.tarifas.ArticuloTarifaServiceException;
import com.comerzzia.pos.services.articulos.tarifas.ArticulosTarifaService;
import com.comerzzia.pos.services.articulos.tarifas.TarifaArticuloDto;
import com.comerzzia.pos.services.codBarrasEsp.CodBarrasEspecialesService;
import com.comerzzia.pos.services.core.usuarios.UsuarioNotFoundException;
import com.comerzzia.pos.services.core.usuarios.UsuariosService;
import com.comerzzia.pos.services.core.usuarios.UsuariosServiceException;
import com.comerzzia.pos.services.promociones.tipos.PromocionLinea;
import com.comerzzia.pos.services.ticket.ITicket;
import com.comerzzia.pos.services.ticket.TicketVentaAbono;
import com.comerzzia.pos.services.ticket.lineas.LineaTicketItemData;
import com.comerzzia.pos.services.ticket.lineas.LineaTicket;
import com.comerzzia.pos.services.ticket.lineas.LineaTicketAbstract;
import com.comerzzia.pos.services.ticket.pagos.PagoTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaCandidataTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.config.SpringContext;
import com.comerzzia.pos.util.format.FormatUtil;
import com.comerzzia.pos.util.i18n.I18N;

public class BasketItemsManager {
	protected static final Logger log = Logger.getLogger(BasketItemsManager.class);

	protected BasketManager basketManager;

	@Autowired
	protected CodBarrasEspecialesService codBarrasEspecialesServices;

	@Autowired
	protected UsuariosService usuariosService;
	
	@Autowired
	protected ModelMapper modelMapper;
	
	@Autowired
	protected ItemService itemService;
	
	@Autowired
    protected ArticulosTarifaService articulosTarifaService;

	protected void setBaskeManager(BasketManager basketManager) {
		this.basketManager = basketManager;
	}

	public BasketItemsUpdateEventDTO newItemRequest(final BasketItemRequestDTO message) throws ApiException {
		String codigo = message.getUpc();

		// check is loyalty card request
		if (isLoyaltyCard(codigo))
			return loyaltyCardRequest(codigo);

		// check is coupon request
		if (isCoupon(codigo))
			return applyCouponRequest(codigo);

		// item sale request
		BasketItemsUpdateEventDTO itemsUpdateEvent = new BasketItemsUpdateEventDTO(basketManager.getBasketTransaction());

		// check is special barcode
		try {
			BasketItemSoldDTO itemSold = isEspecialBarcode(message);

			if (itemSold == null) {
				itemSold = newItemSale(message);
			}

			basketManager.recalculateBasket();

			itemsUpdateEvent.setLastItem(itemSold);
			itemsUpdateEvent.setItemsAffected(getItemsAffected());
		} catch (ItemExceptionDTO e) {
			itemsUpdateEvent.setItemException(e);
		}

		return itemsUpdateEvent;
	}
	
	@SuppressWarnings("unchecked")
	public LineaTicketAbstract createBasketCalculatorLine(BasketItemRequestDTO message) throws ItemExceptionDTO {
		BigDecimal quantity = BigDecimal.ONE;

		if (message.getQuantity() != null) {
			quantity = message.getQuantity();
		}
		
		if (message.getItemData() == null || 
		   (message.getItemData() != null && StringUtils.isBlank(message.getItemData().getItemCode()))) {
			ItemDTO itemDTO = itemService.selectDTOByBarcode(basketManager.getSesion().getUidActividad(), message.getUpc());
			
			if (itemDTO == null) {
				itemDTO = itemService.selectDTOByPrimaryKey(basketManager.getSesion().getUidActividad(), message.getUpc());
				
				if (itemDTO == null) {
					ItemExceptionDTO itemException = new ItemExceptionDTO();
					itemException.setUpc(message.getUpc());
					itemException.setNotFound(true);

					throw itemException;
				}
			} else {
				message.setCombination1Code(itemDTO.getCombination1Code());
				message.setCombination2Code(itemDTO.getCombination2Code());
				
    			if (itemDTO.getDun14()) {
    				// apply DUN14 conversion factor
    				if (itemDTO.getBarcodeConversionFactor() != null && 
    					(itemDTO.getBarcodeConversionFactor().compareTo(BigDecimal.ZERO) > 0)) {
    					quantity = BigDecimalUtil.redondear(quantity.multiply(itemDTO.getBarcodeConversionFactor()), 3);
    				}        				
    			}
			}
			
			
			// update message item data
			message.setItemData(modelMapper.map(itemDTO, BasketItemDataDTO.class));
		}

        TarifaDetalleBean tarifa = null;
        TarifaArticuloDto tarifaArticuloDto = null;
		
		if (message.getRatePrice() == null) {
			// Consultamos tarifa del artículo
			try {
				tarifaArticuloDto = articulosTarifaService.consultarArticuloTarifas(message.getItemData().getItemCode(), 
						                                                            basketManager.getBasketCalculator().getCabecera().getTarifas(), 
						                                                            message.getCombination1Code(), 
						                                                            message.getCombination2Code(),
						                                                            basketManager.getBasketCalculator().getCabecera().getFecha());
				if(tarifaArticuloDto != null) {
					tarifa = tarifaArticuloDto.getDetalle();
				}
				
				if(tarifa.getVersion() == null) { //Si el artículo en base de datos tiene tarifa a null
					tarifa.setVersion(-1l); //Ponemos versión a distinto de null para que no interfiera con el caso de artículo no tarificado
				}
			} catch (ArticuloTarifaNotFoundException | ArticuloTarifaServiceException e) {
				tarifa = new TarifaDetalleBean();
				tarifa.setFactorMarcaje(BigDecimal.ZERO);
				tarifa.setPrecioCosto(BigDecimal.ZERO);
				tarifa.setPrecioTotal(BigDecimal.ZERO);
				tarifa.setPrecioVenta(BigDecimal.ZERO);
				tarifa.setVersion(null); //Ponemos versión a null para diferenciar que no viene de BD
			}
        } else {
        	tarifaArticuloDto = new TarifaArticuloDto();
        	
        	TarifaBean cabecera = new TarifaBean();
        	cabecera.setCodTarifa("GENERAL");
        	cabecera.setPrecioConImpuestos("S");
						
        	tarifa = new TarifaDetalleBean();
        	tarifa.setCodTarifa("GENERAL");            	
        	tarifa.setFactorMarcaje(BigDecimal.ZERO);
			tarifa.setPrecioCosto(BigDecimal.ZERO);
        	tarifa.setPrecioTotal(message.getRatePrice());
        	tarifa.setPrecioVentaRefTotal(message.getReferenceRatePrice());
        	tarifa.setVersion(null);
        	
        	tarifaArticuloDto.setCabecera(cabecera);
        	tarifaArticuloDto.setDetalle(tarifa);
        }
		
		// message validation		
		Assert.notNull(message.getItemData(), "Item data is null");
		Assert.notNull(message.getItemData().getTaxTypeCode(), "Item tax code is null");
		
		if (message.getItemData().getWeightRequired()) {			
			if (message.getWeight() == null) {
				ItemSalesExceptionDTO itemException = new ItemSalesExceptionDTO();
				itemException.setUpc(message.getUpc());
				itemException.setWeightRequired(true);
				itemException.setMessage("Weight required");

				throw itemException;
			}
		}
		
		if (message.getItemData().getGenericItem()) {
			if (message.getPrice() == null) {
				ItemSalesExceptionDTO itemException = new ItemSalesExceptionDTO();
				itemException.setUpc(message.getUpc());
				itemException.setPriceRequired(true);
				itemException.setMessage("Price required for generic item");

				throw itemException;
			}
		}
		
		if ( 
			 (message.getItemData().getCombination1Active() && (StringUtils.isBlank(message.getCombination1Code()) || StringUtils.equals(message.getCombination1Code(), "*"))) ||
			 (message.getItemData().getCombination2Active() && (StringUtils.isBlank(message.getCombination2Code()) || StringUtils.equals(message.getCombination2Code(), "*")))				
		   ) {
			ItemSalesExceptionDTO itemException = new ItemSalesExceptionDTO();
			itemException.setUpc(message.getUpc());			
			itemException.setMessage(I18N.getTexto("Para este artículo se deben especificar sus desgloses."));

			throw itemException;
        }
		
		try {
			LineaTicketAbstract lineaTicket = SpringContext.getBean(LineaTicket.class);
			
			// Construimos línea de ticket
            lineaTicket.setCabecera(basketManager.getBasketCalculator().getCabecera());
            lineaTicket.setEditable(true);
                        
            if(tarifaArticuloDto != null && tarifaArticuloDto.getCabecera() != null) {            	
            	lineaTicket.setIvaIncluido(StringUtils.equals(tarifaArticuloDto.getCabecera().getPrecioConImpuestos(), "S"));
            }
            
            lineaTicket.setArticulo(modelMapper.map(message.getItemData(), LineaTicketItemData.class));
            lineaTicket.setDesglose1(message.getCombination1Code());
            lineaTicket.setDesglose2(message.getCombination2Code());
            lineaTicket.setCantidad(quantity);
            lineaTicket.setTarifa(tarifa);
            lineaTicket.setTarifaOriginal(tarifa);
            lineaTicket.recalcularPreciosImportes();
            //TODO: AttendandId
            lineaTicket.setVendedor(basketManager.getSesion().getSesionUsuario().getUsuario());
            
            if (!StringUtils.equals(message.getUpc(), message.getItemData().getItemCode())) {
            	lineaTicket.setCodigoBarras(message.getUpc());
            }

			if (message.getItemData().getWeightRequired()) {
				lineaTicket.setCantidad(message.getWeight());
				lineaTicket.recalcularImporteFinal();
			} else {
				lineaTicket.setCantidad(quantity);
			}

			if (message.getItemData().getGenericItem()) {
				lineaTicket.resetPromociones();
				lineaTicket.setPrecioTotalConDto(message.getPrice());
				lineaTicket.setPrecioTotalSinDto(message.getPrice());
				lineaTicket.recalcularImporteFinal();
			}

			// set other basket calculator special properties
			setBasketCalculatorLineProperties(message, lineaTicket);

			return lineaTicket;
		} catch (Exception e) {
			log.error("Internal error while inserting line:" + e.getMessage(), e);

			ItemExceptionDTO itemException = new ItemExceptionDTO();
			itemException.setUpc(message.getUpc());
			itemException.setMessage(e.getMessage());

			throw itemException;
		}
	}
	
	public BasketItemSoldDTO newItemSale(BasketItemRequestDTO message) throws ItemExceptionDTO {
		LineaTicketAbstract lineaTicket = createBasketCalculatorLine(message);

		basketManager.addLineToBasketCalculator(lineaTicket);

		return newItemSale((LineaTicket) lineaTicket);
	}
	
	@SuppressWarnings("unchecked")
	public LineaTicket getItemPriceFromBasketCalculator(String codart) throws ItemExceptionDTO {
		TipoDocumentoBean documentoActivo = basketManager.getDocumentoActivo();
		
		@SuppressWarnings("rawtypes")
		ITicket ticketTemporal;

		ticketTemporal = SpringContext.getBean(basketManager.getTicketClass(documentoActivo));
		ticketTemporal.setEsDevolucion(false);
		ticketTemporal.getCabecera().inicializarCabecera(basketManager.getBasketCalculator());
		((TicketVentaAbono) ticketTemporal).inicializarTotales();
		ticketTemporal.setCliente(basketManager.getSesion().getAplicacion().getTienda().getCliente().clone());
		ticketTemporal.setCajero(basketManager.getSesion().getSesionUsuario().getUsuario());
		ticketTemporal.getCabecera().getTotales()
				.setCambio(SpringContext.getBean(PagoTicket.class, basketManager.getSesion().getSesionCaja().getMedioPagoDefecto()));
		ticketTemporal.getTotales().recalcular();
		ticketTemporal.getCabecera().setDocumento(documentoActivo);

		// asignar datos de fidelizado si están asignados
		if (basketManager.getBasketCalculator().getCabecera().getDatosFidelizado() != null) {
			ticketTemporal.getCabecera().setDatosFidelizado(basketManager.getBasketCalculator().getCabecera().getDatosFidelizado());
		}

		BasketItemRequestDTO newItem = new BasketItemRequestDTO();
		newItem.setUpc(codart);
		LineaTicketAbstract linea = createBasketCalculatorLine(newItem);

		return (LineaTicket) linea;
	}

	
	public LineaTicket createAndInsertBasketCalculatorLine(String internalCode, String desglose1, String desglose2, String upc,
			BigDecimal quantity, BigDecimal price) throws ItemExceptionDTO {
		
		BasketItemRequestDTO item = new BasketItemRequestDTO();
		BasketItemDataDTO itemData = new BasketItemDataDTO();
		itemData.setItemCode(internalCode);
		item.setUpc(upc);
		item.setCombination1Code(desglose1);
		item.setCombination2Code(desglose2);				
		item.setQuantity(quantity);
		item.setPrice(price);
		item.setItemData(itemData);
		
		LineaTicketAbstract linea = createBasketCalculatorLine(item);

		return basketManager.addLineToBasketCalculator(linea);
	}

	protected void setBasketCalculatorLineProperties(IBasketItemUpdateRequest<?> item, LineaTicketAbstract lineaTicket) {
		// set serial numbers
		if (lineaTicket instanceof LineaTicket && item.getSerialNumbers() != null
				&& !item.getSerialNumbers().isEmpty()) {
			((LineaTicket) lineaTicket).setNumerosSerie(new ArrayList<String>(item.getSerialNumbers()));
		}
	}

	protected BasketItemSoldDTO lineaTicketToItemSold(LineaTicketAbstract linea) {
		BasketItemSoldDTO itemSold = new BasketItemSoldDTO();

		itemSold.setInternalCode(linea.getCodArticulo());

		itemSold.setUpc(linea.getCodigoBarras() == null ? linea.getCodArticulo() : linea.getCodigoBarras());

		itemSold.setDescription(linea.getDesArticulo());
		itemSold.setCombination1Code(linea.getDesglose1());
		itemSold.setCombination2Code(linea.getDesglose2());
		itemSold.setItemNumber(linea.getIdLinea());
		itemSold.setReferenceRatePrice(null); // actualmente no soportado en la lógica de cálculo
		itemSold.setRatePrice(linea.getPrecioTotalTarifaOrigen());
		itemSold.setPrice(linea.getPrecioConDto());
		itemSold.setExtendedPrice(linea.getImporteTotalConDto());

		itemSold.setManualPrice(linea.getDescuentoManual().compareTo(BigDecimal.ZERO) != 0);
		itemSold.setManualDiscount(linea.getDescuentoManual().compareTo(BigDecimal.ZERO) != 0);

		if (linea.getArticulo().getWeightRequired()) {
			itemSold.setWeight(linea.getCantidad().setScale(3, RoundingMode.DOWN));

			itemSold.setQuantity(new BigDecimal("1"));
		} else {
			// if (linea.getCantidad().compareTo(BigDecimal.ONE) > 0) {
			itemSold.setQuantity(linea.getCantidad());
			// }
		}
		
		itemSold.setItemData(modelMapper.map(linea.getArticulo(), BasketItemDataDTO.class));

		itemSold.setDiscountAmount(linea.getImporteTotalPromociones());

		if (linea.getPromociones().size() > 0) {
			itemSold.setDiscountDescription(linea.getPromociones().get(0).getIdPromocionAsString());

			List<BasketItemDiscountDetailDTO> discountList = new ArrayList<BasketItemDiscountDetailDTO>();

			for (PromocionLineaTicket promocion : linea.getPromociones()) {
				BasketItemDiscountDetailDTO discountDetail = new BasketItemDiscountDetailDTO();
				discountDetail.setOrigin(promocion.getAcceso());
				discountDetail.setUpc(promocion.getCodAcceso());

				discountDetail.setDiscountType(promocion.getIdTipoPromocion());
				
				discountDetail.setDescription(promocion.getTextoPromocion());
				
				discountDetail.setQuantity(promocion.getCantidadPromocion());
				discountDetail.setDiscountQuantity(promocion.getCantidadPromocionAplicada());

				discountDetail.setPointsObtained(promocion.getPuntos());
				discountDetail.setDiscountDestination(promocion.getTipoDescuento());

				discountDetail.setExclusive(promocion.isExclusiva());

				if (promocion.isDescuentoFuturo()) {
					discountDetail.setAmount(promocion.getImporteTotalDtoFuturo());
				} else if (promocion.isDescuentoMenosIngreso()) {
					discountDetail.setAmount(promocion.getImporteTotalDtoMenosIngreso());
				} else {
					discountDetail.setAmount(promocion.getImporteTotalDtoMenosMargen());
				}

				discountList.add(discountDetail);
			}

			itemSold.setDiscountList(discountList);
		}

		if (linea.getPromocionesAplicables().size() > 0) {
			List<BasketItemDiscountDetailDTO> discountList = new ArrayList<BasketItemDiscountDetailDTO>();

			for (PromocionLineaCandidataTicket promocionCandidata : linea.getPromocionesAplicables()) {
				PromocionLinea promocion = promocionCandidata.getPromocion();
				
				BasketItemDiscountDetailDTO discountDetail = new BasketItemDiscountDetailDTO();
				discountDetail.setOrigin("PROMOTION");
				discountDetail.setUpc(promocion.getIdPromocion().toString());

				discountDetail.setDiscountType(promocion.getIdTipoPromocion());
				discountDetail.setDescription(promocion.getDescripcion());
				
				discountDetail.setDiscountDestination(promocion.getTipoDto());

				discountDetail.setExclusive(promocion.isExclusiva());

				discountList.add(discountDetail);
			}
			
			itemSold.setPotentialDiscounts(discountList);
		}

		itemSold.setAttendantId(linea.getVendedor().getUsuario());

		return itemSold;
	}

	public void updateItemsSoldDiscounts() {
		log.debug("updateItemsSoldDiscounts()");
		getItemsAffected();
	}

	@SuppressWarnings("unchecked")
	protected List<BasketItemSoldDTO> getItemsAffected() {
		List<BasketItemSoldDTO> linesAffected = null;

		// check prices changes
		for (LineaTicket ticketLine : (List<LineaTicket>) basketManager.getBasketCalculator().getLineas()) {
			BasketItemSoldDTO currentItemSold = basketManager.getBasketTransaction().getItems().get(ticketLine.getIdLinea());

			if (currentItemSold != null) {
				BasketItemSoldDTO newItemSold = lineaTicketToItemSold(ticketLine);

				BigDecimal actualDiscount = currentItemSold.getDiscountAmount();
				BigDecimal newDiscount = newItemSold.getDiscountAmount();

				// compare cached values & send changes
				if (actualDiscount.compareTo(newDiscount) != 0) {
					log.debug("Updating line " + ticketLine.getIdLinea());

					if (linesAffected == null) {
						linesAffected = new ArrayList<BasketItemSoldDTO>();
					}

					linesAffected.add(newItemSold);

					// update basket
					basketManager.getBasketTransaction().getItems().put(ticketLine.getIdLinea(), newItemSold);
				}
			}
		}

		return linesAffected;
	}

	protected BasketItemSoldDTO newItemSale(final LineaTicket newLine) {
		// send new line & totals
		BasketItemSoldDTO itemSold = lineaTicketToItemSold(newLine);

		// add new item to basket
		basketManager.getBasketTransaction().getItems().put(newLine.getIdLinea(), itemSold);

		return itemSold;
	}

	protected BasketItemSoldDTO isEspecialBarcode(BasketItemRequestDTO message) throws ItemExceptionDTO {
		String codigo = message.getUpc();

		CodigoBarrasEspecialBean codBarrasEspecial = null;
		try {
			codBarrasEspecial = codBarrasEspecialesServices.esCodigoBarrasEspecial(codigo);
		} catch (Exception e) {
			log.error(String.format(
					"checkCodigoBarrasEspecial() - Error inesperado %s tratando código de barras especial %s",
					e.getMessage(), codigo), e);
		}

		if (codBarrasEspecial == null)
			return null;

		BigDecimal cantidad = null;
		BigDecimal precio = null;
		boolean disableWeightItemFlag = false;

		String sku = codBarrasEspecial.getCodart();

		if (sku == null) {
			log.error(String.format(
					"checkCodigoBarrasEspecial() - El código de barra especial obtenido no es válido. CodArticulo: %s",
					sku));
			return null;
		}

		String cantCodBar = codBarrasEspecial.getCantidad();

		if (cantCodBar != null) {
			cantidad = FormatUtil.getInstance().desformateaBigDecimal(cantCodBar, 3);
		} else {
			if (message.getWeight() != null) {
				cantidad = message.getWeight();
			}
		}

		String precioCodBar = codBarrasEspecial.getPrecio();

		if (precioCodBar != null) {
			if (cantidad == null) {
				cantidad = BigDecimal.ONE;
				disableWeightItemFlag = true;
			}
			precio = FormatUtil.getInstance().desformateaBigDecimal(codBarrasEspecial.getPrecio(), 2);
		}

		LineaTicketAbstract linea = getItemPriceFromBasketCalculator(sku);

		// Check if item is weight required
		if (cantidad == null && linea.getArticulo().getWeightRequired()) {
			ItemSalesExceptionDTO itemException = new ItemSalesExceptionDTO();
			itemException.setUpc(codigo);
			itemException.setWeightRequired(true);

			throw itemException;
		}

		// if sku is barcode, change to internal item code
		if (!StringUtils.equals(linea.getCodArticulo(), sku)) {
			sku = linea.getCodArticulo();
		}

		if (cantidad == null) {
			cantidad = BigDecimal.ONE;
		}

		// TODO: reescribir con nueva lógica unificada
		linea = createAndInsertBasketCalculatorLine(sku, linea.getDesglose1(), linea.getDesglose2(), codigo, cantidad, precio);

		if (disableWeightItemFlag && linea.getArticulo().getWeightRequired()) {
			// disable kg/price flag
			linea.getArticulo().setWeightRequired(false);
		}

		return newItemSale((LineaTicket) linea);
	}

	protected Boolean isLoyaltyCard(String code) {
		return false;
	}

	protected BasketItemsUpdateEventDTO loyaltyCardRequest(String code) throws ApiException {
		BasketItemsUpdateEventDTO itemsUpdateEvent = new BasketItemsUpdateEventDTO(basketManager.getBasketTransaction());

		LoyaltyCardDTO loyaltyCard;
		try {
			loyaltyCard = loyaltyCard(code);
		} catch (LoyaltyCardExceptionDTO e) {
			throw new BadRequestException(e.getMessage());
		}
		
		itemsUpdateEvent.setLoyaltyCard(loyaltyCard);
		itemsUpdateEvent.setItemsAffected(getItemsAffected());

		return itemsUpdateEvent;

	}

	protected LoyaltyCardDTO loyaltyCard(String code) throws LoyaltyCardExceptionDTO {
		try {
			//TODO: AMA, DELEGAR FIDELIZACION
			LoyaltyCardDTO loyaltyCard = new LoyaltyCardDTO();

			basketManager.getBasketTransaction().setLoyaltyCard(loyaltyCard);
			
			return loyaltyCard;
		} catch (IllegalArgumentException e) {
			LoyaltyCardExceptionDTO loyaltyCardException = new LoyaltyCardExceptionDTO();
			loyaltyCardException.setUpc(code);
			loyaltyCardException.setInactive(true);

			loyaltyCardException.setMessage(e.getMessage());
			throw loyaltyCardException;
		} catch (Exception e) {
			log.error("nuevoCodigoArticulo() - Ha habido un error al leer la tarjeta de fidelizado: " + e.getMessage(),
					e);

			LoyaltyCardExceptionDTO loyaltyCardException = new LoyaltyCardExceptionDTO();
			loyaltyCardException.setUpc(code);
			loyaltyCardException.setItemType("LoyaltyCard");
			loyaltyCardException.setMessage(I18N.getTexto("Ha habido un error al leer la tarjeta de fidelizado"));

			throw loyaltyCardException;
		}
	}

	protected boolean isCoupon(String code) {
		return basketManager.getSesion().getSesionPromociones().isCoupon(code);
	}

	public BasketItemsUpdateEventDTO applyCouponRequest(String code) throws ApiException {
//		if (!basketManager.applyCoupon(code)) {
//			throw new BadRequestException("The coupon " + code + " can not be applied");
//		}
//
		BasketItemsUpdateEventDTO itemsUpdateEvent = new BasketItemsUpdateEventDTO(basketManager.getBasketTransaction());
//
//		itemsUpdateEvent.setItemsAffected(getItemsAffected());
//
		return itemsUpdateEvent;
	}

	public BasketItemsUpdateEventDTO voidItemRequest(BasketItemVoidRequestDTO voidItem) throws ApiException {
		BasketItemVoidedDTO itemVoided = voidItem(voidItem);

		BasketItemsUpdateEventDTO itemsUpdateEvent = new BasketItemsUpdateEventDTO(basketManager.getBasketTransaction());

		itemsUpdateEvent.addItemVoided(itemVoided);

		itemsUpdateEvent.setItemsAffected(getItemsAffected());

		return itemsUpdateEvent;
	}

	public BasketItemVoidedDTO voidItem(BasketItemVoidRequestDTO voidItem) throws ApiException {
		basketManager.deleteItem(voidItem.getItemNumber());

		BasketItemVoidedDTO itemVoided = new BasketItemVoidedDTO();
		itemVoided.setUpc(voidItem.getUpc());
		itemVoided.setItemNumber(voidItem.getItemNumber());

		return itemVoided;
	}

	public BasketItemsUpdateEventDTO itemSaleUpdateRequest(int itemNumber, BasketItemSaleUpdateRequestDTO itemSaleUpdateRequest)
			throws ApiException {
		LineaTicketAbstract lineaTicket = (LineaTicketAbstract) basketManager.basketCalculator.getLinea(itemNumber);

		if (itemSaleUpdateRequest.getQuantity() != null) {
			lineaTicket.setCantidad(itemSaleUpdateRequest.getQuantity());
		}

		if (itemSaleUpdateRequest.getPrice() != null) {
			lineaTicket.resetPromociones();
			lineaTicket.setPrecioTotalConDto(itemSaleUpdateRequest.getPrice());
			lineaTicket.setPrecioTotalSinDto(itemSaleUpdateRequest.getPrice());
		}

		if (itemSaleUpdateRequest.getDiscountPercentage() != null) {
			lineaTicket.setDescuentoManual(itemSaleUpdateRequest.getDiscountPercentage());
		}

		if (itemSaleUpdateRequest.getAttendantId() != null) {
			try {
				lineaTicket.setVendedor(usuariosService.consultarUsuario(basketManager.getSesion(),
						itemSaleUpdateRequest.getAttendantId()));
			} catch (UsuarioNotFoundException | UsuariosServiceException e) {
				throw new BadRequestException("AttendantId not found");
			}
		}

		// set other basket calculator special properties
		setBasketCalculatorLineProperties(itemSaleUpdateRequest, lineaTicket);

		lineaTicket.recalcularImporteFinal();

		basketManager.updateBasketCalculatorLine(itemNumber, lineaTicket);

		// update item in basket
		BasketItemSoldDTO newItemSold = lineaTicketToItemSold(lineaTicket);
		basketManager.getBasketTransaction().getItems().put(itemNumber, newItemSold);

		// generate basket update event
		BasketItemsUpdateEventDTO itemsUpdateEvent = new BasketItemsUpdateEventDTO(basketManager.getBasketTransaction());
		itemsUpdateEvent.setLastItem(newItemSold);
		itemsUpdateEvent.setItemsAffected(getItemsAffected());

		return itemsUpdateEvent;
	}
	
	// TODO: Tratar signo de las cantidades que llegan
    protected BigDecimal tratarSignoCantidad(BigDecimal valor, String codTipoDoc, Boolean esDevolucion){
    	BigDecimal valorRetorno = valor;

		TipoDocumentoBean doc = basketManager.getDocumentoActivo();

		if (doc.isSignoPositivo()) {
			valorRetorno = valorRetorno.abs();
		}
		else if (doc.isSignoNegativo()) {
			valorRetorno = valorRetorno.abs().negate();
		}
		else if (esDevolucion) {
			valorRetorno = valorRetorno.abs().negate();
		}
		
		return valorRetorno;
    }

}
