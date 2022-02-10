package com.kasnady.returnapi.testreturnapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kasnady.returnapi.testreturnapi.entity.Order;
import com.kasnady.returnapi.testreturnapi.entity.OrderKey;

public interface OrderRepository extends CrudRepository<Order, OrderKey> {

	public List<Order> findByOrderIdAndEmail(String orderId, String email);
}
