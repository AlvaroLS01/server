package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketItem {

	private String itemCode;
	private String ean;
	private String itemDescription;
	private BigDecimal quantity;
	private String combination1Code;
	private String combination2Code;
	private String order;
	private Promotions promotions;
	private String taxTypeCode;
	private BigDecimal priceWithoutDTO;
	private BigDecimal totalPriceWithoutDTO;
	private BigDecimal price;
	private BigDecimal totalPrice;
	private BigDecimal amount;
	private BigDecimal totalAmount;
	private BigDecimal discount;
	private String unitMeasureQuantity;
	private String unitOfMeasure;
	private BigDecimal unitPrice;
	private int origenTicketLine;
	
	@JsonProperty("reason")
	private Reason reason;

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getCombination1Code() {
		return combination1Code;
	}

	public void setCombination1Code(String combination1Code) {
		this.combination1Code = combination1Code;
	}

	public String getCombination2Code() {
		return combination2Code;
	}

	public void setCombination2Code(String combination2Code) {
		this.combination2Code = combination2Code;
	}

	public int getOrder() {
		return Integer.parseInt(order);
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Promotions getPromotions() {
		return promotions;
	}

	public void setPromotions(Promotions promotions) {
		this.promotions = promotions;
	}

	public String getTaxTypeCode() {
		return taxTypeCode;
	}

	public void setTaxTypeCode(String taxTypeCode) {
		this.taxTypeCode = taxTypeCode;
	}

	public BigDecimal getPriceWithoutDTO() {
		return priceWithoutDTO;
	}

	public void setPriceWithoutDTO(BigDecimal priceWithoutDTO) {
		this.priceWithoutDTO = priceWithoutDTO;
	}

	public BigDecimal getTotalPriceWithoutDTO() {
		return totalPriceWithoutDTO;
	}

	public void setTotalPriceWithoutDTO(BigDecimal totalPriceWithoutDTO) {
		this.totalPriceWithoutDTO = totalPriceWithoutDTO;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public String getUnitMeasureQuantity() {
		return unitMeasureQuantity;
	}

	public void setUnitMeasureQuantity(String unitMeasureQuantity) {
		this.unitMeasureQuantity = unitMeasureQuantity;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getOrigenTicketLine() {
		return origenTicketLine;
	}

	public void setOrigenTicketLine(int origenTicketLine) {
		this.origenTicketLine = origenTicketLine;
	}

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public Reason getReason() {
		return reason;
	}

	public void setReason(Reason reason) {
		this.reason = reason;
	}

}
