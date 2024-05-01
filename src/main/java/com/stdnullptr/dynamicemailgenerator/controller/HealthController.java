package com.stdnullptr.dynamicemailgenerator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/health")
@Slf4j
public class HealthController {

	@GetMapping
	public ResponseEntity<Void> healthCheck() {
		log.info("Health check called");
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}