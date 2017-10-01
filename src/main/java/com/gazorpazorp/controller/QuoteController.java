package com.gazorpazorp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gazorpazorp.model.Quote;
import com.gazorpazorp.model.dto.QuoteDto;
import com.gazorpazorp.model.dtoMapper.QuoteMapper;
import com.gazorpazorp.service.QuoteService;

@RestController
@RequestMapping("/api/deliveries")
public class QuoteController {
	
	@Autowired
	QuoteService quoteService;
	
	@PostMapping("/quote")
	public ResponseEntity<QuoteDto> createSample () throws Exception {
		return Optional.ofNullable(quoteService.createQuote())
				.map(o -> new ResponseEntity<QuoteDto>(QuoteMapper.INSTANCE.quoteToQuoteDto(o), HttpStatus.OK))
				.orElseThrow(() -> new Exception("Could not create quote!"));
	}	
}
