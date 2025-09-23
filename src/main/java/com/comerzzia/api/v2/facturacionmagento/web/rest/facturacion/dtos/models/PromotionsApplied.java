package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromotionsApplied {

	private List<PromotionApplied> promotionApplied;

	public List<PromotionApplied> getPromotionApplied() {
		return promotionApplied;
	}

	public void setPromotionApplied(List<PromotionApplied> promotionApplied) {
		this.promotionApplied = promotionApplied;
	}
}
