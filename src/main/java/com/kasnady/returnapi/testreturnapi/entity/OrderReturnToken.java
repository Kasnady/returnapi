package com.kasnady.returnapi.testreturnapi.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "order_return_tokens", uniqueConstraints = @UniqueConstraint(columnNames = { "returnToken" }), indexes = {
		@Index(columnList = "returnToken") })
@IdClass(OrderReturnTokenId.class)
public class OrderReturnToken {
	@Id
	@Column(nullable = false)
	private String orderId;
	@Id
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String returnToken;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@Transient
	private double totalAmount;
	@Transient
	private List<ReturnOrder> returnOrders;

	public OrderReturnToken() {
		super();
	}

	public OrderReturnToken(String orderId, String email, String token) {
		this.orderId = orderId;
		this.email = email;
		this.returnToken = token;
	}

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

	public String getReturnToken() {
		return returnToken;
	}

	public void setReturnToken(String returnToken) {
		this.returnToken = returnToken;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<ReturnOrder> getReturnOrders() {
		return returnOrders;
	}

	public void setReturnOrders(List<ReturnOrder> returnOrders) {
		this.returnOrders = returnOrders;
	}
}