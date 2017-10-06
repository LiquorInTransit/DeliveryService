package com.gazorpazorp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gazorpazorp.model.Delivery;
import com.gazorpazorp.model.Quote;
import com.gazorpazorp.service.DeliveryService;
import com.gazorpazorp.service.QuoteService;

@RestController
@RequestMapping("/internal/deliveries")
public class InternalDeliveryController {
	
	@Autowired
	QuoteService quoteService;
	@Autowired
	DeliveryService deliveryService;

	//TODO: Make this endpoint @ /internal/deliveries/quote/{id}
	@GetMapping("/quote/{id}")
	@PreAuthorize("#oauth2.hasScope('system')")
	public ResponseEntity<Quote> getQuote (@PathVariable("id") Long id) throws Exception {
		return Optional.ofNullable(quoteService.getQuoteById(id))
				.map(q -> new ResponseEntity<Quote>(q, HttpStatus.OK))
				.orElseThrow(() -> new Exception ("The quote of that ID does not exist"));
	}
	
	
	@PostMapping
	@PreAuthorize("#oauth2.hasScope('system')")
	public ResponseEntity createDelivery(@RequestParam("quoteId")Long quoteId, @RequestParam("orderId")Long orderId) throws Exception {
		return Optional.ofNullable(deliveryService.createDelivery(quoteId, orderId))
				.map(d -> new ResponseEntity(d, HttpStatus.OK))
				.orElseThrow(() -> new Exception("Failed to create Delivery"));
	}
	
	@GetMapping("/order/{orderId}")
	@PreAuthorize("#oauth2.hasScope('system')")
	public ResponseEntity getDeliveries(@PathVariable(value="orderId") Long orderId) throws Exception {
			return Optional.ofNullable(deliveryService.getDeliveryByOrderId(orderId, false))
					.map(d -> new ResponseEntity<Delivery>(d, HttpStatus.OK))
					.orElseThrow(() -> new Exception("Delivery with specified OrderId does not exist"));
		
	}
	
	@DeleteMapping("/order/{orderId}")
	@PreAuthorize("#oauth2.hasScope('system')")
	public ResponseEntity deleteDelivery(@PathVariable("orderId") Long orderId) throws Exception {
			return Optional.ofNullable(deliveryService.deleteDeliveryByOrderId(orderId))
					.map(d -> new ResponseEntity(HttpStatus.OK))
					.orElseThrow(() -> new Exception("Failed to delete delivery"));
		
	}
}
