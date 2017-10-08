package com.gazorpazorp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gazorpazorp.model.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long>{
	public Delivery findByOrderId(@Param("orderId") Long orderId);
	public void deleteByOrderId(@Param("orderId") Long orderId);
	
	public List<Delivery> findByDropoffCustomerId(@Param("dropoff.customerId") Long id);
	
	public List<Delivery> findByDriverId(@Param("driverId") Long id);
	
	public Delivery findTopByDriverIdIsNullAndDriverHoldIsNullOrderByCreatedAtAsc();
	
	@Query("select d from Delivery d where d.driverId = ?1 and status != 'complete'")
	public Delivery findCurrentDeliveryForDriver(Long driverId);
}
