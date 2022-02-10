package com.kasnady.returnapi.testreturnapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.kasnady.returnapi.testreturnapi.repository.OrderRepository;
import com.kasnady.returnapi.testreturnapi.service.impl.OrderServiceImpl;
import com.kasnady.returnapi.testreturnapi.service.impl.ReturnOrderServiceImpl;

@SpringBootApplication
public class TestReturnapiApplication {

	@Autowired
	OrderServiceImpl orderService;
	@Autowired
	ReturnOrderServiceImpl returnOrderService;

	public static void main(String[] args) {
		// Start Application
		SpringApplication.run(TestReturnapiApplication.class, args);
	}

	@Bean
	public CommandLineRunner orderRepoRunner(OrderRepository repository) {
		return args -> {
			// initiate return status
			returnOrderService.loadStatus();
			// Load initial orders from CSV
			orderService.loadOrders();
		};
	}
}
