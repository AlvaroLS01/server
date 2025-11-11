package com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Promotion {

	private BigDecimal discountAmount;
	private String percentageDiscount;
	private long dtoType;
	private int promotionId;

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getPercentageDiscount() {
		return percentageDiscount;
	}

	public void setPercentageDiscount(String percentageDiscount) {
		this.percentageDiscount = percentageDiscount;
	}

	public long getDtoType() {
		return dtoType;
	}

	public void setDtoType(long dtoType) {
		this.dtoType = dtoType;
	}

	public int getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(int promotionId) {
		this.promotionId = promotionId;
	}
}
