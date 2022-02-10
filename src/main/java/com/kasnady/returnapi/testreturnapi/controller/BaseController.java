package com.kasnady.returnapi.testreturnapi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.google.gson.JsonObject;

public class BaseController {
	final Logger log = LogManager.getLogger(BaseController.class);

	/**
	 * Succeed response
	 * 
	 * @param obj
	 * @return
	 */
	public ResponseEntity<Object> succeedResponse(JsonObject obj) {
		log.info("Succeed response: {}", obj);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(obj.toString());
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
