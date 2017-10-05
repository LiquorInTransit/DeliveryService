package com.gazorpazorp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gazorpazorp.model.Delivery;
import com.gazorpazorp.model.Quote;
import com.gazorpazorp.model.dto.QuoteDto;
import com.gazorpazorp.model.dtoMapper.QuoteMapper;
import com.gazorpazorp.service.DeliveryService;
import com.gazorpazorp.service.QuoteService;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {
	
	@Autowired
	QuoteService quoteService;
	@Autowired
	DeliveryService deliveryService;
	
	@PostMapping(value="/quote", produces="application/json")
	public ResponseEntity<QuoteDto> createQuote () throws Exception {
		return Optional.ofNullable(quoteService.createQuote())
				.map(o -> new ResponseEntity<QuoteDto>(QuoteMapper.INSTANCE.quoteToQuoteDto(o), HttpStatus.OK))
				.orElseThrow(() -> new Exception("Could not create quote!"));
	}	
	
	@GetMapping
	public ResponseEntity getDeliveries(@RequestParam(value="orderId") Long orderId) throws Exception {
			return Optional.ofNullable(deliveryService.getDeliveryByOrderId(orderId, true))
					.map(d -> new ResponseEntity<Delivery>(d, HttpStatus.OK))
					.orElseThrow(() -> new Exception("Delivery with specified OrderId does not exist"));
		
	}
	

}
