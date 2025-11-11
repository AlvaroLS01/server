package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * { "reason": { "code": "97", "type_code": "2", "description": "Prueba JGG Cambio Precio Manual 1", "comment":
 * "Cambio precio 1", "original_item_price": 208.00, "item_price_applied": 100.00 } }
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reason {

	@JsonProperty("code")
	private String code;

	@JsonProperty("type_code")
	private String typeCode;

	@JsonProperty("description")
	private String description;

	@JsonProperty("comment")
	private String comment;

	@JsonProperty("original_item_price")
	private String originalItemPrice;

	@JsonProperty("item_price_applied")
	private String itemPriceApplied;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
