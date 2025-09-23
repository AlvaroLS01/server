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
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.FiscalData;

public class FacturacionRequest {

	private IdentificationCards identificationCards;
	private InvoiceData invoiceData;
	private DeliveryData deliveryData;
	private LoyaltyDetails loyaltyDetails;
	private PaymentsData paymentsData;
	private PromotionsSummary promotionsSummary;
	private Seller seller;
	private Store store;
	private FiscalDataItems fiscalData;
	private Ticket ticket;

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

	public void setNewFiscalData(FiscalData fiscalData) {
		setFiscalData(new FiscalDataItems(fiscalData));
	}

	public FiscalDataItems getFiscalDataItems() {
		return fiscalData;
	}

	public void setFiscalData(FiscalDataItems fiscalData) {
		this.fiscalData = fiscalData;
	}
}
