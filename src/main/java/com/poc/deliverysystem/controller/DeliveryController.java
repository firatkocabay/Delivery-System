package com.poc.deliverysystem.controller;

import com.poc.deliverysystem.model.dto.DeliveryDetailResponseDto;
import com.poc.deliverysystem.model.dto.DeliveryRequestDto;
import com.poc.deliverysystem.model.dto.DeliveryResponseDto;
import com.poc.deliverysystem.service.DeliveryService;
import org.apache.tomcat.util.codec.binary.Base64;
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
    public ResponseEntity<List<DeliveryDetailResponseDto>> getDeliveryList(@RequestHeader("Authorization") String authentication, @RequestParam Map<String,String> params) {
        String pair = new String(Base64.decodeBase64(authentication.substring(6)));
        String companyId = pair.split(":")[1];
        return new ResponseEntity<>(deliveryService.getDeliveriesByCriteria(params, companyId), HttpStatus.OK);
    }

    @PostMapping("/v1/delivery")
    public ResponseEntity<DeliveryResponseDto> createDelivery(@RequestHeader("Authorization") String authentication, @RequestBody DeliveryRequestDto deliveryRequestDto) {
        String pair = new String(Base64.decodeBase64(authentication.substring(6)));
        String companyId = pair.split(":")[1];
        return new ResponseEntity<>(deliveryService.createNewDelivery(deliveryRequestDto, companyId), HttpStatus.OK);
    }

}
