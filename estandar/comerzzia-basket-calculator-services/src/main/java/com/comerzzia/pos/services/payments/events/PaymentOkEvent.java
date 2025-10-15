package com.comerzzia.pos.services.payments.events;

import java.math.BigDecimal;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

public class PaymentOkEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3660381588818399949L;

	protected int paymentId;

	protected BigDecimal amount;

	protected Map<String, Object> extendedData;

	protected boolean canceled;

	protected boolean removable;

	public PaymentOkEvent(Object source, int paymentId, BigDecimal amount) {
		super(source);
		this.paymentId = paymentId;
		this.amount = amount;
		this.removable = true;
		this.extendedData = new HashMap<String, Object>();
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Map<String, Object> getExtendedData() {
		return extendedData;
	}

	public void setExtendedData(Map<String, Object> extendedData) {
		this.extendedData = extendedData;
	}

	public void addExtendedData(String key, Object value) {
		if (this.extendedData == null) {
			this.extendedData = new HashMap<String, Object>();
		}

		this.extendedData.put(key, value);
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public boolean isRemovable() {
		return removable;
	}

	public void setRemovable(boolean removable) {
		this.removable = removable;
	}

}
