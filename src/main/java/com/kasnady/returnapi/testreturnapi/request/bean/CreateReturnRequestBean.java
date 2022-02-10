package com.kasnady.returnapi.testreturnapi.request.bean;

import java.util.List;

import com.kasnady.returnapi.testreturnapi.entity.Order;

public class CreateReturnRequestBean {
	private String returnToken;
	private List<Order> orders;

	public String getReturnToken() {
		return returnToken;
	}

	public void setReturnToken(String returnToken) {
		this.returnToken = returnToken;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}
