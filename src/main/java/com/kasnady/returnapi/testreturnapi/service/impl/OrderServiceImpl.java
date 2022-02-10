package com.kasnady.returnapi.testreturnapi.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.kasnady.returnapi.testreturnapi.entity.Order;
import com.kasnady.returnapi.testreturnapi.entity.OrderReturnToken;
import com.kasnady.returnapi.testreturnapi.repository.OrderRepository;
import com.kasnady.returnapi.testreturnapi.repository.OrderReturnTokenRepository;
import com.kasnady.returnapi.testreturnapi.service.OrderService;
import com.kasnady.returnapi.testreturnapi.util.FileResourceUtil;

@Service
public class OrderServiceImpl implements OrderService {
	final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

	@Autowired
	OrderRepository orderRepo;
	@Autowired
	OrderReturnTokenRepository orderReturnTokenRepo;

	/**
	 * Generate Token for OrderID & Email to use later on for creating return
	 * transaction
	 * 
	 * @param orderId
	 * @param email
	 * @return
	 */
	public String generateToken(String orderId, String email) {
		// Generate token
		String token = UUID.randomUUID().toString();
		OrderReturnToken oToken = new OrderReturnToken(orderId, email, token);
		orderReturnTokenRepo.save(oToken);

		logger.info("Generated token: {} for OrderId: {} Email: {}", token, orderId, email);
		return token;
	}

	/**
	 * Load orders from CSV file to prevent loading when received API call
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	public void loadOrders() throws IOException {
		logger.info("AK# Load orders called! Getting file");
		FileResourceUtil frUtil = new FileResourceUtil();
		String fileName = "files/orders.csv";
		String line = "";
		String splitBy = ",";

		// parsing a CSV file into BufferedReader class constructor
		InputStream is = frUtil.getFileUriFromResourceAsStream(fileName);
		try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(streamReader)) {

			boolean isFirst = true;
			while ((line = reader.readLine()) != null) // returns a Boolean value
			{
				// Skip if empty
				if (!StringUtils.hasText(line) || isFirst) {
					isFirst = false;
					continue;
				}

				// Save to DB if not empty
				logger.info("AK# Saving order: {}", line);
				String[] o = line.split(splitBy); // use comma as separator
				Order newOrder = new Order(o[0], // orderId
						o[2], // sku
						o[1], // email
						Integer.valueOf(o[3]), // quantity
						Double.valueOf(o[4]), // price
						o[5] // itemName
				);
				orderRepo.save(newOrder);
			}
		}
	}
}
