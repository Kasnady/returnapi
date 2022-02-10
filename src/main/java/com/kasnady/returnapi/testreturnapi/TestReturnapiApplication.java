package com.kasnady.returnapi.testreturnapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.kasnady.returnapi.testreturnapi.repository.OrderRepository;
import com.kasnady.returnapi.testreturnapi.service.impl.OrderServiceImpl;

@SpringBootApplication
public class TestReturnapiApplication {

	@Autowired
	OrderRepository orderRepo;
	@Autowired
	OrderServiceImpl orderService;

	public static void main(String[] args) {
		// Start Application
		SpringApplication.run(TestReturnapiApplication.class, args);
	}

	@Bean
	public CommandLineRunner orderRepoRunner(OrderRepository repository) {
		return (args) ->
		// Load initial orders from CSV
		orderService.loadOrders();
	}
}
