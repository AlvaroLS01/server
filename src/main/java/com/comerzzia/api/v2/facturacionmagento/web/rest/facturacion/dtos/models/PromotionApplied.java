package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromotionApplied {

	private String cuponPromotionId;
	private double discountAmount;
	private String percentageDiscount;
	private int promotionId;

	public String getCuponPromotionId() {
		return cuponPromotionId;
	}

	public void setCuponPromotionId(String cuponPromotionId) {
		this.cuponPromotionId = cuponPromotionId;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getPercentageDiscount() {
		return percentageDiscount;
	}

	public void setPercentageDiscount(String percentageDiscount) {
		this.percentageDiscount = percentageDiscount;
	}

	public int getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(int promotionId) {
		this.promotionId = promotionId;
	}
}
