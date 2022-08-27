package com.poc.deliverysystem.service;

import com.poc.deliverysystem.model.dto.DeliveryDetailResponseDto;
import com.poc.deliverysystem.model.dto.DeliveryRequestDto;
import com.poc.deliverysystem.model.dto.DeliveryResponseDto;

import java.util.List;
import java.util.Map;

public interface DeliveryService {

    List<DeliveryDetailResponseDto> getDeliveriesByCriteria(Map<String,String> queryCriteria);

    DeliveryResponseDto createNewDelivery(DeliveryRequestDto deliveryRequestDto);

}
