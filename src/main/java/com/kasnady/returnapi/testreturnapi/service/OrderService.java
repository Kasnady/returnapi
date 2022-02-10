package com.kasnady.returnapi.testreturnapi.service;

import java.io.IOException;

public interface OrderService {
	String generateToken(String orderId, String email);

	void loadOrders() throws IOException;
}
