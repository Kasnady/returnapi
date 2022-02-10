package com.kasnady.returnapi.testreturnapi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.google.gson.JsonObject;

public class BaseController {
	final Logger log = LogManager.getLogger(BaseController.class);

	/**
	 * Succeed response with String
	 * 
	 * @param obj
	 * @return
	 */
	public ResponseEntity<Object> succeedResponse(String response) {
		log.info("Succeed response: {}", response);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}

	/**
	 * Succeed response with JsonObject
	 * 
	 * @param obj
	 * @return
	 */
	public ResponseEntity<Object> succeedResponse(JsonObject obj) {
		return this.succeedResponse(obj.toString());
	}

	/**
	 * Failed response
	 * 
	 * @param message
	 * @return
	 */
	public ResponseEntity<Object> badResponse(String message) {
		log.warn("Bad response: {}", message);
		JsonObject obj = new JsonObject();
		obj.addProperty("message", message);
		return ResponseEntity.badRequest().body(obj.toString());
	}
}
