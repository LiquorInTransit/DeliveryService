package com.gazorpazorp.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gazorpazorp.model.Customer;

@FeignClient(name="gateway-service")
public interface GatewayClient {
	
	@GetMapping(value="/api/me", consumes = "application/json")
	Customer getCustomer();
}

