package com.gazorpazorp.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Delivery {

	private Long id;
	private Long quoteId;
	private Long orderId;
	private Long driverId;
	
	private Timestamp createdAt;
	
	@Embedded
	//@Column(name="pickup")
	private Pickup pickup;
	@Embedded
	//@Column(name="dropoff")
	private Dropoff dropoff;
	
	private Double fee;
	private String status;
	
	private String trackingURL;
	
	
	public Delivery () {}
	
	@PrePersist
	public void onCreate() {
		this.setCreatedAt(new Timestamp(new Date().getTime()));
	}

	@Id
	@GenericGenerator(name = "incrementGenerator", strategy = "org.hibernate.id.IncrementGenerator")
	@GeneratedValue(generator="incrementGenerator")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Long getQuoteId() {
		return quoteId;
	}
	public void setQuoteId(Long quoteId) {
		this.quoteId = quoteId;
	}

	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public Long getDriverId() {
		return driverId;
	}
	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public Pickup getPickup() {
		return pickup;
	}
	public void setPickup(Pickup pickup) {
		this.pickup = pickup;
	}

	public Dropoff getDropoff() {
		return dropoff;
	}
	public void setDropoff(Dropoff dropoff) {
		this.dropoff = dropoff;
	}

	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrackingURL() {
		return trackingURL;
	}
	public void setTrackingURL(String trackingURL) {
		this.trackingURL = trackingURL;
	}
	
	
	
	
	
}
