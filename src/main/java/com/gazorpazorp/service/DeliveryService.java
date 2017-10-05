package com.gazorpazorp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gazorpazorp.client.GatewayClient;
import com.gazorpazorp.model.Customer;
import com.gazorpazorp.model.Delivery;
import com.gazorpazorp.model.Quote;
import com.gazorpazorp.repository.DeliveryRepository;
import com.gazorpazorp.repository.QuoteRepository;

@Service
public class DeliveryService {
	Logger logger = LoggerFactory.getLogger(DeliveryService.class);

	@Autowired
	QuoteRepository quoteRepo;
	@Autowired
	DeliveryRepository deliveryRepo;
	
	@Autowired
	GatewayClient accountClient;

	// @Autowired
	// DeliveryTrackerClient deliveryTrackerClient;

	public String createDelivery(Long quoteId, Long orderId) {
		Quote quote = quoteRepo.findById(quoteId).get();

		Delivery delivery = new Delivery();
		delivery.setPickup(quote.getPickup());
		delivery.setDropoff(quote.getDropoff());
		delivery.setQuoteId(quote.getId());
		delivery.setOrderId(orderId);
		delivery.setFee(quote.getFee());
		delivery = deliveryRepo.save(delivery);
		// TODO: REMOVE THIS AWFUL TESTING SHIT
		delivery.setTrackingURL("localhost:8080/api/tracking/1");
		// delivery.setTrackingURL(deliveryTrackerClient.track(delivery.getId()))
		// if (delivery.getTrackingURL() != null)
		deliveryRepo.save(delivery);

		return delivery.getTrackingURL();
	}

	public Delivery getDeliveryByOrderId(Long orderId, boolean verifyCustomer) {
		Delivery delivery = deliveryRepo.findByOrderId(orderId);
		
		if (verifyCustomer) {
			//validate that the accountId of the order belongs to the user
			try {
				validateCustomerId(delivery.getDropoff().getCustomerId());
			} catch (Exception e) {
				// TODO: Make this throw an exception so that feign can say that you're not
				// authorized to look at these orders
				logger.error("FAILED VALIDATION");
				return null;
			}
		}

		return delivery;
	}
	
	@Transactional
	public Boolean deleteDeliveryByOrderId(Long orderId) {
		deliveryRepo.deleteByOrderId(orderId);
		Delivery delivery = deliveryRepo.findByOrderId(orderId);
		if (delivery == null)
			return true;

		return null;
	}
	
	public List<Delivery> getDeliveriesForCustomer() {
		return deliveryRepo.findByDropoffCustomerId(accountClient.getCustomer().getId());
	}
	
	private boolean validateCustomerId(Long customerId) throws Exception {
		Customer customer = accountClient.getCustomer();
		
		if (customer != null && customer.getId() != customerId) {
			throw new Exception ("Account number not valid");
		}
		return true;
	}
}
