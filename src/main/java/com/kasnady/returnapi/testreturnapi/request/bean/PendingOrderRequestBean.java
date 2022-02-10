package com.kasnady.returnapi.testreturnapi.request.bean;

public class PendingOrderRequestBean {
	private String orderId;
	private String email;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
