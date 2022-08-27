package com.poc.deliverysystem.repository;

import com.poc.deliverysystem.model.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeliveryRepository extends JpaRepository<Delivery, String>, JpaSpecificationExecutor<Delivery> {

}
