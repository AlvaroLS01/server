package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos;

import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.FiscalDataItems;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.DeliveryData;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.IdentificationCards;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.InvoiceData;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.LoyaltyDetails;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.PaymentsData;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.PromotionsSummary;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.Seller;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.Store;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.Ticket;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacturacionResponse {

	private String uidTicket;
	private String codTicket;
	private String idLocator;
	private IdentificationCards identificationCards;
	private InvoiceData invoiceData;
	private DeliveryData deliveryData;
	private LoyaltyDetails loyaltyDetails;
	private PaymentsData paymentsData;
	private PromotionsSummary promotionsSummary;
	private Seller seller;
	private FiscalDataItems fiscalData;
	private Store store;
	private Ticket ticket;

	/* Atributos para devolver errores */
	private String code;
	private String type;
	private String message;

	public FacturacionResponse() {
		uidTicket = null;
		codTicket = null;
		identificationCards = null;
		invoiceData = null;
		deliveryData = null;
		loyaltyDetails = null;
		paymentsData = null;
		promotionsSummary = new PromotionsSummary();
		store = null;
		ticket = null;
		code = null;
		type = null;
		message = null;
	}

	/* Constructor para devolver errores */
	public FacturacionResponse(String msg) {
		uidTicket = null;
		codTicket = null;
		identificationCards = null;
		invoiceData = null;
		loyaltyDetails = null;
		paymentsData = null;
		promotionsSummary = null;
		store = null;
		ticket = null;
		code = "1";
		type = "error";
		message = msg;
	}

	public String getUidTicket() {
		return uidTicket;
	}

	public void setUidTicket(String uidTicket) {
		this.uidTicket = uidTicket;
	}

	public String getCodTicket() {
		return codTicket;
	}

	public void setCodTicket(String codTicket) {
		this.codTicket = codTicket;
	}

	public String getIdLocator() {
		return idLocator;
	}

	public void setIdLocator(String idLocator) {
		this.idLocator = idLocator;
	}

	public IdentificationCards getIdentificationCards() {
		return identificationCards;
	}

	public void setIdentificationCards(IdentificationCards identificationCards) {
		this.identificationCards = identificationCards;
	}

	public InvoiceData getInvoiceData() {
		return invoiceData;
	}

	public void setInvoiceData(InvoiceData invoiceData) {
		this.invoiceData = invoiceData;
	}

	public DeliveryData getDeliveryData() {
		return deliveryData;
	}

	public void setDeliveryData(DeliveryData deliveryData) {
		this.deliveryData = deliveryData;
	}

	public LoyaltyDetails getLoyaltyDetails() {
		return loyaltyDetails;
	}

	public void setLoyaltyDetails(LoyaltyDetails loyaltyDetails) {
		this.loyaltyDetails = loyaltyDetails;
	}

	public PaymentsData getPaymentsData() {
		return paymentsData;
	}

	public void setPaymentsData(PaymentsData paymentsData) {
		this.paymentsData = paymentsData;
	}

	public PromotionsSummary getPromotionsSummary() {
		return promotionsSummary;
	}

	public void setPromotionsSummary(PromotionsSummary promotionsSummary) {
		this.promotionsSummary = promotionsSummary;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public FiscalDataItems getFiscalDataItems() {
		return fiscalData;
	}

	public void setFiscalDataItems(FiscalDataItems fiscalData) {
		this.fiscalData = fiscalData;
	}

}
