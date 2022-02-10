package com.kasnady.returnapi.testreturnapi.service;

public interface OrderService {
	String generateToken(String orderId, String email);
}
