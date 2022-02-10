package com.kasnady.returnapi.testreturnapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.kasnady.returnapi.testreturnapi.entity.OrderReturnToken;
import com.kasnady.returnapi.testreturnapi.entity.OrderReturnTokenId;

public interface OrderReturnTokenRepository extends CrudRepository<OrderReturnToken, OrderReturnTokenId> {

	public OrderReturnToken findOneByOrderIdAndEmail(String orderId, String email);
}
