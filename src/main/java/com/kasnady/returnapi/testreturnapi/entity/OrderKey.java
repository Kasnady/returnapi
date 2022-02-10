package com.kasnady.returnapi.testreturnapi.entity;

import java.io.Serializable;
import java.util.Objects;

public class OrderKey implements Serializable {
	private static final long serialVersionUID = 8354169718865934769L;

	private String orderId;
	private String sku;

	public OrderKey() {
	}

	public OrderKey(String orderId, String sku) {
		this.orderId = orderId;
		this.sku = sku;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		OrderKey oKey = (OrderKey) o;
		return orderId.equals(oKey.orderId) && sku.equals(oKey.sku);
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, sku);
	}
}
