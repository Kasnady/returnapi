package com.kasnady.returnapi.testreturnapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.kasnady.returnapi.testreturnapi.entity.ReturnOrder;

public interface ReturnOrderRepository extends CrudRepository<ReturnOrder, Long> {

	public ReturnOrder findOneByOrderIdAndSku(String orderId, String sku);

	public boolean existsByReturnToken(String returnToken);
}
