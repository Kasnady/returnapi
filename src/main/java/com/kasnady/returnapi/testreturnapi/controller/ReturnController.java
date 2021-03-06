package com.kasnady.returnapi.testreturnapi.controller;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.kasnady.returnapi.testreturnapi.entity.OrderReturnToken;
import com.kasnady.returnapi.testreturnapi.entity.ReturnStatus;
import com.kasnady.returnapi.testreturnapi.exception.ServiceClientException;
import com.kasnady.returnapi.testreturnapi.repository.OrderReturnTokenRepository;
import com.kasnady.returnapi.testreturnapi.request.bean.CreateReturnRequestBean;
import com.kasnady.returnapi.testreturnapi.request.bean.UpdateReturnItemRequestBean;
import com.kasnady.returnapi.testreturnapi.service.OrderService;
import com.kasnady.returnapi.testreturnapi.service.ReturnOrderService;
import com.kasnady.returnapi.testreturnapi.util.JsonUtil;

@RestController
@RequestMapping(path = "/returns", consumes = "application/json", produces = "application/json")
public class ReturnController extends BaseController {
	final Logger logger = LogManager.getLogger(ReturnController.class);

	@Autowired
	OrderReturnTokenRepository orderReturnTokenRepo;
	@Autowired
	OrderService orderService;
	@Autowired
	ReturnOrderService returnOrderService;

	/**
	 * Create return transaction and return it with Refund amount
	 * 
	 * @param orderId
	 * @param email
	 * @param model
	 * @return
	 */
	@PostMapping(value = { "", "/" })
	public ResponseEntity<Object> createReturnTxn(@RequestBody CreateReturnRequestBean returnReqBean) {
		logger.info("Called create return transaction with ReturnToken: {}", returnReqBean.getReturnToken());
		try {
			this.validateCreateReturnTxn(returnReqBean);

			// Check whether token transaction exist
			OrderReturnToken oReturnToken = orderReturnTokenRepo.findOneByReturnToken(returnReqBean.getReturnToken());
			if (oReturnToken == null) {
				throw new ServiceClientException("Return Token not found!");
			}
			logger.info("Return token recognized! In record OrderID: {} Email: {} Amount: {}",
					oReturnToken.getOrderId(), oReturnToken.getEmail(), oReturnToken.getTotalAmount());

			oReturnToken = returnOrderService.createReturnOrderAndGetSummary(oReturnToken, returnReqBean.getOrders());
			return succeedResponse(JsonUtil.gson.toJson(oReturnToken));
		} catch (ServiceClientException e) {
			return badResponse(e.getMessage());
		}
	}

	/**
	 * Get created return transaction
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = { "/{id}" })
	public ResponseEntity<Object> getReturnTxn(@PathVariable String id) {
		logger.info("Called get return transaction for ReturnToken: {}", id);
		OrderReturnToken oReturnToken = returnOrderService.getReturnOrders(id);
		return succeedResponse(oReturnToken == null ? null : JsonUtil.gson.toJson(oReturnToken));
	}

	/**
	 * Update return item
	 * 
	 * @param id
	 * @param itemId
	 * @param returnItemReqBean
	 * @return
	 */
	@PutMapping(value = { "/{id}/items/{itemId}/qc/status" })
	public ResponseEntity<Object> updateReturnTxnItem(@PathVariable String id, @PathVariable Long itemId,
			@RequestBody UpdateReturnItemRequestBean returnItemReqBean) {
		logger.info("Called update return item transaction with ReturnToken: {} for itemId: {} with statusId: {}", id,
				itemId, returnItemReqBean.getStatusId());
		try {
			this.validateUpdateReturnItemTxn(returnItemReqBean);

			logger.info("Request for update return order passed validation!");
			returnOrderService.updateReturnOrder(itemId, id, returnItemReqBean.getStatusId());

			JsonObject responseObj = new JsonObject();
			responseObj.addProperty("status", "OK");
			return succeedResponse(responseObj);
		} catch (ServiceClientException e) {
			return badResponse(e.getMessage());
		}
	}

	/**
	 * Validate request for create return transaction
	 * 
	 * @param returnReqBean
	 * @throws ServiceClientException
	 */
	private void validateCreateReturnTxn(CreateReturnRequestBean returnReqBean) throws ServiceClientException {
		// Validate is Order empty
		if (returnReqBean.getOrders() == null || returnReqBean.getOrders().isEmpty()) {
			throw new ServiceClientException("Return Orders empty!");
		}

		// Validate is Qty empty
		boolean isValid = returnReqBean.getOrders().stream()
				.filter(o -> o.getQuantity() == null || o.getQuantity() <= 0 || !StringUtils.hasText(o.getEmail())
						|| !StringUtils.hasText(o.getOrderId()) || !StringUtils.hasText(o.getSku()))
				.count() <= 0;
		if (!isValid) {
			throw new ServiceClientException("Return Orders structure invalid! Missing OrderID/SKU/Email/Quantity");
		}

		// Validate is SKU duplicate
		Set<String> skus = new HashSet<>();
		returnReqBean.getOrders().stream().forEach(o -> skus.add(o.getSku()));
		if (skus.size() != returnReqBean.getOrders().size()) {
			throw new ServiceClientException("Duplicate SKU detected!");
		}
	}

	/**
	 * Validate update request for return item transaction
	 * 
	 * @param returnReqBean
	 * @throws ServiceClientException
	 */
	private void validateUpdateReturnItemTxn(UpdateReturnItemRequestBean returnItemReqBean)
			throws ServiceClientException {
		// Validate is Status ID empty and correct value
		if (returnItemReqBean.getStatusId() == null) {
			throw new ServiceClientException("Invalid Status ID provided!");
		}
		int statusId = returnItemReqBean.getStatusId();
		if (!(ReturnStatus.ACCEPTED.equals(statusId) || ReturnStatus.REJECTED.equals(statusId))) {
			throw new ServiceClientException("Only allow to update to ACCEPTED / REJECTED!");
		}
	}
}
