package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromotionsSummary {

	private PromotionsApplied promotionsApplied;

	public PromotionsApplied getPromotionsApplied() {
		return promotionsApplied;
	}

	public void setPromotionsApplied(PromotionsApplied promotionsApplied) {
		this.promotionsApplied = promotionsApplied;
	}
}
