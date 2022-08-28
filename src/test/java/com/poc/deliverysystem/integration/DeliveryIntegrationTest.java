package com.poc.deliverysystem.integration;

import com.poc.deliverysystem.controller.DeliveryController;
import com.poc.deliverysystem.model.dto.*;
import com.poc.deliverysystem.service.CompanyService;
import com.poc.deliverysystem.service.DeliveryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DeliveryIntegrationTest {

    @LocalServerPort
    private int port;
    @Autowired
    private DeliveryController deliveryController;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        assertNotNull(deliveryController);
        assertNotNull(companyService);
        assertNotNull(deliveryService);
    }

    @Test
    void createDelivery() {
        CompanyRequestDto requestDto = new CompanyRequestDto("lpr7448", "abc 12345", "567 tre");
        CompanyResponseDto newCompany = companyService.createNewCompany(requestDto);
        DeliveryRequestDto deliveryRequestDto = new DeliveryRequestDto("215970", "test","567 tre","ytu 765");

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(newCompany.getUserName(), newCompany.getPassword());
        HttpEntity<?> request = new HttpEntity<>(deliveryRequestDto, headers);
        ResponseEntity<DeliveryResponseDto> responseDto = restTemplate
                .exchange("http://localhost:" + port + "/v1/delivery", HttpMethod.POST,
                request, DeliveryResponseDto.class);

        assertNotNull(responseDto.getBody());
        assertNotNull(responseDto.getBody().getDeliveryId());

    }
    @Test
    void searchDelivery() {
        CompanyRequestDto requestDto = new CompanyRequestDto("lpr736", "abc 12345", "567 tre");
        CompanyResponseDto newCompany = companyService.createNewCompany(requestDto);

        DeliveryRequestDto deliveryRequestDto = new DeliveryRequestDto("454970", "test","567 tre","ytu 765");
        deliveryService.createNewDelivery(deliveryRequestDto, newCompany.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(newCompany.getUserName(), newCompany.getPassword());

        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<List<DeliveryDetailResponseDto>> responseDto = restTemplate
                .exchange("http://localhost:" + port + "/v1/delivery?orderId=454970", HttpMethod.GET,
                request, new ParameterizedTypeReference<List<DeliveryDetailResponseDto>>(){});

        assertNotNull(responseDto.getBody());
        assertEquals(responseDto.getBody().get(0).getOrderId(), "454970");

    }

}