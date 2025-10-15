package com.comerzzia.pos.services.core.sesion.coupons.application;

import java.util.ArrayList;
import java.util.List;

import com.comerzzia.pos.persistence.fidelizacion.CustomerCouponDTO;

public class CouponsApplicationResultDTO {

	private List<CustomerCouponDTO> appliedCoupons;

	private List<CustomerCouponDTO> errorCoupons;

	public List<CustomerCouponDTO> getAppliedCoupons() {
		return appliedCoupons;
	}

	public void setAppliedCoupons(List<CustomerCouponDTO> appliedCoupons) {
		this.appliedCoupons = appliedCoupons;
	}

	public List<CustomerCouponDTO> getErrorCoupons() {
		return errorCoupons;
	}

	public void setErrorCoupons(List<CustomerCouponDTO> errorCoupons) {
		this.errorCoupons = errorCoupons;
	}
	
	public void addAppliedCoupon(CustomerCouponDTO coupon) {
		if(appliedCoupons == null) {
			appliedCoupons = new ArrayList<CustomerCouponDTO>();
		}
		appliedCoupons.add(coupon);
	}
	
	public void addErrorCoupon(CustomerCouponDTO coupon) {
		if(errorCoupons == null) {
			errorCoupons = new ArrayList<CustomerCouponDTO>();
		}
		errorCoupons.add(coupon);
	}

}
