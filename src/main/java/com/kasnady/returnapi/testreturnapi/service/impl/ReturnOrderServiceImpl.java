package com.kasnady.returnapi.testreturnapi.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kasnady.returnapi.testreturnapi.entity.Order;
import com.kasnady.returnapi.testreturnapi.entity.OrderReturnToken;
import com.kasnady.returnapi.testreturnapi.entity.ReturnOrder;
import com.kasnady.returnapi.testreturnapi.entity.ReturnStatus;
import com.kasnady.returnapi.testreturnapi.exception.ServiceClientException;
import com.kasnady.returnapi.testreturnapi.repository.OrderRepository;
import com.kasnady.returnapi.testreturnapi.repository.OrderReturnTokenRepository;
import com.kasnady.returnapi.testreturnapi.repository.ReturnOrderRepository;
import com.kasnady.returnapi.testreturnapi.repository.ReturnStatusRepository;
import com.kasnady.returnapi.testreturnapi.service.ReturnOrderService;
import com.kasnady.returnapi.testreturnapi.util.JsonUtil;

@Service
public class ReturnOrderServiceImpl implements ReturnOrderService {
	final Logger logger = LogManager.getLogger(ReturnOrderServiceImpl.class);

	@Autowired
	OrderRepository orderRepo;
	@Autowired
	OrderReturnTokenRepository orderReturnTokenRepo;
	@Autowired
	ReturnOrderRepository returnOrderRepo;
	@Autowired
	ReturnStatusRepository returnStatusRepo;

	public OrderReturnToken createReturnOrderAndGetSummary(OrderReturnToken oReturnToken, List<Order> orders)
			throws ServiceClientException {
		List<ReturnOrder> returnOrders = this.createReturnOrders(oReturnToken, orders);
		if (returnOrders.isEmpty()) {
			throw new ServiceClientException("Provided orders invalid!");
		}
		logger.info("Calculate summary of return order for ReturnToken: {}", oReturnToken.getReturnToken());

		double summary = returnOrders.stream().mapToDouble(ro -> ro.getQuantity() * ro.getPrice()).sum();

		oReturnToken.setTotalAmount(summary);
		orderReturnTokenRepo.save(oReturnToken);
		logger.info("ReturnToken: {} saved with summary amount: {}", oReturnToken.getReturnToken(),
				oReturnToken.getTotalAmount());
		oReturnToken.setReturnOrders(returnOrders);
		return oReturnToken;
	}

	/**
	 * Create Return Orders
	 * 
	 * @param oReturnToken
	 * @param orders
	 * @return
	 */
	private List<ReturnOrder> createReturnOrders(OrderReturnToken oReturnToken, List<Order> orders)
			throws ServiceClientException {
		List<ReturnOrder> returnOrders = new LinkedList<>();

		logger.info("Filter & ONLY get order detail match with order in DB");
		List<Order> filteredOrders = orders.stream()
				.filter(o -> o.getOrderId().equalsIgnoreCase(oReturnToken.getOrderId())
						&& o.getEmail().equalsIgnoreCase(oReturnToken.getEmail()))
				.collect(Collectors.toList());

		logger.info("Check whether this order already created as Return Order");
		filteredOrders.stream().forEach(fo -> {
			ReturnOrder ro = returnOrderRepo.findOneByOrderIdAndSku(fo.getOrderId(), fo.getSku());
			if (ro != null) {
				logger.warn("OrderID: {} with SKU: {} already created as Return Order using ReturnToken: {}",
						fo.getOrderId(), fo.getSku(), ro.getReturnToken());
				return;
			}

			logger.info("OrderID: {} with SKU: {} return order not yet created! Check whether this order really exist",
					fo.getOrderId(), fo.getSku());
			Order o = orderRepo.findOneByOrderIdAndSku(fo.getOrderId(), fo.getSku());
			if (o == null) {
				logger.warn("OrderID: {} with SKU: {} not exist!", fo.getOrderId(), fo.getSku());
				return;
			} else if (o.getQuantity() < fo.getQuantity()) {
				logger.warn("OrderID: {} with SKU: {}, Qty: {} over than DB Qty: {} should have!", fo.getOrderId(),
						fo.getSku(), fo.getQuantity(), o.getQuantity());
				return;
			}

			ReturnStatus rs = returnStatusRepo.findById(ReturnStatus.AWAITING_APPROVAL).get();
			ro = new ReturnOrder();
			ro.setReturnToken(oReturnToken.getReturnToken());
			ro.setOrderId(fo.getOrderId());
			ro.setSku(fo.getSku());
			ro.setQuantity(fo.getQuantity()); // Return QTY requested
			ro.setPrice(o.getPrice()); // This should be store again, incase master data price was modified
			ro.setReturnStatus(rs);
			returnOrders.add(ro);
			logger.info("Return Order added: {}", JsonUtil.gson.toJson(ro));
		});

		if (!returnOrders.isEmpty()) {
			if (returnOrders.size() > orders.size()) {
				throw new ServiceClientException(
						"Return Orders provided include orders that had been requested before!");
			}
			returnOrderRepo.saveAll(returnOrders);
		}
		return returnOrders;
	}

	/**
	 * Load return status into DB
	 */
	public void loadStatus() {
		logger.info("AK# Load return order status called!");
		List<ReturnStatus> statuses = ReturnStatus.getStatuses();
		returnStatusRepo.saveAll(statuses);
	}
}
