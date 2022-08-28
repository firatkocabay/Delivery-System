package com.poc.deliverysystem.service;

import com.poc.deliverysystem.model.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DeliveryServiceTest {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private CompanyService companyService;

    @Test
    void contextLoads() {
        assertNotNull(deliveryService);
        assertNotNull(companyService);
    }

    @Test
    void givenDeliveryRequestWhenCallCreateDeliveryThenReturnNewDelivery() {
        CompanyRequestDto companyRequestDto = new CompanyRequestDto("xyz 3456", "abc 12345", "567 tre");
        CompanyResponseDto newCompany = companyService.createNewCompany(companyRequestDto);

        DeliveryRequestDto deliveryRequestDto = new DeliveryRequestDto("ab345", "test","567 tre","ytu 765");
        DeliveryResponseDto newDelivery = deliveryService.createNewDelivery(deliveryRequestDto, newCompany.getPassword());

        assertNotNull(newDelivery);
    }

    @Test
    void givenOrderIdWhenSearchDeliveriesThenShouldReturnRelatedDeliveries() {
        CompanyRequestDto requestDto = new CompanyRequestDto("xyz", "abc 12345", "567 tre");
        CompanyResponseDto newCompany = companyService.createNewCompany(requestDto);

        DeliveryRequestDto deliveryRequestDto = new DeliveryRequestDto("cd345", "test","567 tre","ytu 765");
        DeliveryResponseDto newDelivery = deliveryService.createNewDelivery(deliveryRequestDto, newCompany.getPassword());

        Map<String, String> params = new HashMap<>();
        params.put("orderId", deliveryRequestDto.getOrderId());

        List<DeliveryDetailResponseDto> deliveriesByCriteria = deliveryService.getDeliveriesByCriteria(params, newCompany.getPassword());

        assertNotNull(deliveriesByCriteria);
        assertEquals(deliveriesByCriteria.get(0).getDeliveryId(), newDelivery.getDeliveryId());
        assertEquals(deliveriesByCriteria.get(0).getOrderId(), deliveryRequestDto.getOrderId());

    }

}