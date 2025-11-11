package com.comerzzia.api.v2.facturacionmagento.persistence.cupones;

public class CuponBean {

	private String couponCode;
	private Long promotionId;
	private String couponDescription;
	private String couponTypeCode;
	
	public String getCouponTypeCode() {
		return couponTypeCode;
	}
	
	public void setCouponTypeCode(String couponTypeCode) {
		this.couponTypeCode = couponTypeCode;
	}

	public String getCouponCode() {
		return couponCode;
	}
	
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	
	public Long getPromotionId() {
		return promotionId;
	}
	
	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}
	
	public String getCouponDescription() {
		return couponDescription;
	}
	
	public void setCouponDescription(String coupon_description) {
		this.couponDescription = coupon_description;
	}
	
}
