package com.kasnady.returnapi.testreturnapi.service;

import java.util.List;

import com.kasnady.returnapi.testreturnapi.entity.Order;
import com.kasnady.returnapi.testreturnapi.entity.OrderReturnToken;
import com.kasnady.returnapi.testreturnapi.entity.ReturnOrder;
import com.kasnady.returnapi.testreturnapi.exception.ServiceClientException;

public interface ReturnOrderService {
	OrderReturnToken createReturnOrderAndGetSummary(OrderReturnToken oReturnToken, List<Order> orders)
			throws ServiceClientException;

	double getReturnOrderRefundAmount(List<ReturnOrder> returnOrders);

	OrderReturnToken getReturnOrders(String returnToken);

	void loadStatus();

	boolean updateReturnOrder(Long id, String returnToken, int statusId) throws ServiceClientException;
}
