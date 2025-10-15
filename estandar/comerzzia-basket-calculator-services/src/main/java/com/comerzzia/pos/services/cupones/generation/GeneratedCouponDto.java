package com.comerzzia.pos.services.cupones.generation;

import java.math.BigDecimal;
import java.util.Date;

public class GeneratedCouponDto {

	private String couponTypeCode;

	private Long promotionId;

	private Date startDate;

	private Date endDate;

	private String couponCode;

	private BigDecimal couponAmount;

	public GeneratedCouponDto(String codigoCupon, BigDecimal importeCupon) {
		super();
		this.couponCode = codigoCupon;
		this.couponAmount = importeCupon;
	}

	public String getCouponTypeCode() {
		return couponTypeCode;
	}

	public void setCouponTypeCode(String couponTypeCode) {
		this.couponTypeCode = couponTypeCode;
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

}
