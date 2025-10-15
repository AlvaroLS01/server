package com.comerzzia.pos.services.core.sesion.coupons.types;

public class CouponTypeDTO {

	private String couponTypeCode;

	private String couponTypeName;

	private Boolean defManualSelect;

	private String prefix;

	private Integer maxLength;

	private Integer generationMode;

	public String getCouponTypeCode() {
		return couponTypeCode;
	}

	public void setCouponTypeCode(String couponTypeCode) {
		this.couponTypeCode = couponTypeCode;
	}

	public String getCouponTypeName() {
		return couponTypeName;
	}

	public void setCouponTypeName(String couponTypeName) {
		this.couponTypeName = couponTypeName;
	}

	public Boolean getDefManualSelect() {
		return defManualSelect;
	}

	public void setDefManualSelect(Boolean defManualSelect) {
		this.defManualSelect = defManualSelect;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public Integer getGenerationMode() {
		return generationMode;
	}

	public void setGenerationMode(Integer generationMode) {
		this.generationMode = generationMode;
	}

}
