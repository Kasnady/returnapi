package com.kasnady.returnapi.testreturnapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kasnady.returnapi.testreturnapi.entity.ReturnOrder;

public interface ReturnOrderRepository extends CrudRepository<ReturnOrder, Long> {

	public boolean existsByReturnToken(String returnToken);

	public List<ReturnOrder> findByReturnToken(String returnToken);

	public ReturnOrder findOneByOrderIdAndSku(String orderId, String sku);

	public ReturnOrder findOneByIdAndReturnToken(Long id, String returnToken);
}
