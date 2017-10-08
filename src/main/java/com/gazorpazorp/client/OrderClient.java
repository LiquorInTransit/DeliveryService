package com.gazorpazorp.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gazorpazorp.client.config.TokenRequestClientConfiguration;
import com.gazorpazorp.model.LineItem;

@FeignClient(name="order-service", configuration = TokenRequestClientConfiguration.class)
public interface OrderClient {
	
	@GetMapping("/internal/orders/{orderId}/items")
	public List<LineItem> getItemsInOrder(@PathVariable("orderId") Long orderId);
}
