package com.comerzzia.pos.persistence.fidelizacion;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerCouponDTO {

	private String couponCode;

	private String couponName;

	private String couponDescription;

	private Date startDate;

	private Date endDate;

	private Boolean manualSelection;

	private Long promotionId;

	private Long priority;

	private BigDecimal balance;

	private String imageUrl;

	private Boolean active;

	private Date creationDate;

	private Long loyalCustomerId;

	private Date creationtDate;

	private boolean validationRequired;

	public CustomerCouponDTO(String couponCode, boolean validationRequired) {
		super();
		this.couponCode = couponCode;
		this.validationRequired = validationRequired;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getCouponDescription() {
		return couponDescription;
	}

	public void setCouponDescription(String couponDescription) {
		this.couponDescription = couponDescription;
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

	public Boolean getManualSelection() {
		return manualSelection;
	}

	public void setManualSelection(Boolean manualSelection) {
		this.manualSelection = manualSelection;
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getLoyalCustomerId() {
		return loyalCustomerId;
	}

	public void setLoyalCustomerId(Long loyalCustomerId) {
		this.loyalCustomerId = loyalCustomerId;
	}

	public Date getCreationtDate() {
		return creationtDate;
	}

	public void setCreationtDate(Date creationtDate) {
		this.creationtDate = creationtDate;
	}

	public boolean isValidationRequired() {
		return validationRequired;
	}

	public void setValidationRequired(boolean validationRequired) {
		this.validationRequired = validationRequired;
	}

}
