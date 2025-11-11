package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * "event": { "date": "2023-09-11T09:49:36.554+02:00", "type": "CAMBIO_PRECIO", "event_description":
 * "Cambio de precio autorizado por JBN", "applicable": "true", "user_id": "10006", "user_description": "JBN",
 * "supervisor_id": "10006", "supervisor_description": "JBN", "article_code": "0050375019978", "article_description":
 * "TRITURADOR RESIDUOS FREG M46", "article_quantity": 1.000; "original_item_price": 208.00, "item_price_applied":
 * 100.00 }
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {

	@JsonProperty("date")
	private String date;

	@JsonProperty("type")
	private String type;

	@JsonProperty("event_description")
	private String eventDescription;

	@JsonProperty("applicable")
	private String applicable;

	@JsonProperty("user_id")
	private String userId;

	@JsonProperty("user_description")
	private String userDescription;

	@JsonProperty("supervisor_id")
	private String supervisorId;

	@JsonProperty("supervisor_description")
	private String supervisorDescription;

	@JsonProperty("article_code")
	private String articleCode;

	@JsonProperty("article_description")
	private String articleDescription;

	@JsonProperty("article_quantity")
	private String articleQuantity;

	@JsonProperty("original_item_price")
	private String originalItemPrice;

	@JsonProperty("item_price_applied")
	private String itemPriceApplied;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getApplicable() {
		return applicable;
	}

	public void setApplicable(String applicable) {
		this.applicable = applicable;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserDescription() {
		return userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	public String getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(String supervisorId) {
		this.supervisorId = supervisorId;
	}

	public String getSupervisorDescription() {
		return supervisorDescription;
	}

	public void setSupervisorDescription(String supervisorDescription) {
		this.supervisorDescription = supervisorDescription;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public String getArticleDescription() {
		return articleDescription;
	}

	public void setArticleDescription(String articleDescription) {
		this.articleDescription = articleDescription;
	}

	public String getArticleQuantity() {
		return articleQuantity;
	}

	public void setArticleQuantity(String articleQuantity) {
		this.articleQuantity = articleQuantity;
	}

	public String getOriginalItemPrice() {
		return originalItemPrice;
	}

	public void setOriginalItemPrice(String originalItemPrice) {
		this.originalItemPrice = originalItemPrice;
	}

	public String getItemPriceApplied() {
		return itemPriceApplied;
	}

	public void setItemPriceApplied(String itemPriceApplied) {
		this.itemPriceApplied = itemPriceApplied;
	}

}
