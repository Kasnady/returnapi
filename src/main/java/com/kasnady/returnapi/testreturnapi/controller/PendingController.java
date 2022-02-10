package com.kasnady.returnapi.testreturnapi.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.kasnady.returnapi.testreturnapi.entity.Order;
import com.kasnady.returnapi.testreturnapi.exception.ServiceClientException;
import com.kasnady.returnapi.testreturnapi.repository.OrderRepository;
import com.kasnady.returnapi.testreturnapi.request.bean.PendingOrderRequestBean;
import com.kasnady.returnapi.testreturnapi.service.OrderService;

@RestController
@RequestMapping(path = "/pending", consumes = "application/json", produces = "application/json")
public class PendingController extends BaseController {
	final Logger logger = LogManager.getLogger(PendingController.class);

	@Autowired
	OrderRepository orderRepo;
	@Autowired
	OrderService orderService;

	/**
	 * Generate Token to be use for creating return transaction
	 * 
	 * @param orderId
	 * @param email
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/returns")
	public ResponseEntity<Object> generatePendingReturnToken(@RequestBody PendingOrderRequestBean pendingOrderReq) {
		logger.info("Pending return called with OrderID: {} Email: {}", pendingOrderReq.getOrderId(),
				pendingOrderReq.getEmail());

		try {
			// Get record from DB
			List<Order> orders = orderRepo.findByOrderIdAndEmail(pendingOrderReq.getOrderId(),
					pendingOrderReq.getEmail());
			if (orders != null && !orders.isEmpty()) {
				logger.info("Orders found for OrderID: {} Email: {}! Generating token", pendingOrderReq.getOrderId(),
						pendingOrderReq.getEmail());
				Order o = orders.get(0);

				// Generating token
				JsonObject responseObj = new JsonObject();
				responseObj.addProperty("token", orderService.generateToken(o.getOrderId(), o.getEmail()));
				return succeedResponse(responseObj);
			} else {
				throw new ServiceClientException("Order ID with Email provided was not found!");
			}
		} catch (ServiceClientException e) {
			return badResponse(e.getMessage());
		}
	}
}
