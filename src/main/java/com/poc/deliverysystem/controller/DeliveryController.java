package com.poc.deliverysystem.controller;

import com.poc.deliverysystem.model.dto.DeliveryDetailResponseDto;
import com.poc.deliverysystem.model.dto.DeliveryRequestDto;
import com.poc.deliverysystem.model.dto.DeliveryResponseDto;
import com.poc.deliverysystem.service.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/v1/delivery")
    public ResponseEntity<List<DeliveryDetailResponseDto>> getDeliveryList(@RequestParam Map<String,String> params) {
        return new ResponseEntity<>(deliveryService.getDeliveriesByCriteria(params), HttpStatus.OK);
    }

    @PostMapping("/v1/delivery")
    public ResponseEntity<DeliveryResponseDto> createDelivery(@RequestBody DeliveryRequestDto deliveryRequestDto) {
        return new ResponseEntity<>(deliveryService.createNewDelivery(deliveryRequestDto), HttpStatus.OK);
    }

}
