package com.kasnady.returnapi.testreturnapi.entity;

import java.io.Serializable;
import java.util.Objects;

public class OrderReturnTokenId implements Serializable {
	private static final long serialVersionUID = -3005853471973846305L;
	private String orderId;
	private String email;

	public OrderReturnTokenId() {
	}

	public OrderReturnTokenId(String orderId, String email) {
		this.orderId = orderId;
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		OrderReturnTokenId orderReturnTokenId = (OrderReturnTokenId) o;
		return orderId.equals(orderReturnTokenId.orderId) && email.equals(orderReturnTokenId.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, email);
	}
}