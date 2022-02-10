package com.kasnady.returnapi.testreturnapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="orders")
@IdClass(OrderKey.class)
public class Order {
	@Id
	@Column(nullable = false)
	private String orderId;
	@Id
	@Column(nullable = false)
	private String sku;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private Integer quantity;
	@Column(nullable = false)
	private double price;
	@Column(nullable = false)
	private String itemName;
	@Column
	private String returnToken;

	public Order() {
		super();
	}

	public Order(String orderId, String sku, String email, Integer quantity, double price, String itemName) {
		super();
		this.orderId = orderId;
		this.sku = sku;
		this.email = email;
		this.quantity = quantity;
		this.price = price;
		this.itemName = itemName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getReturnToken() {
		return returnToken;
	}

	public void setReturnToken(String returnToken) {
		this.returnToken = returnToken;
	}
}
