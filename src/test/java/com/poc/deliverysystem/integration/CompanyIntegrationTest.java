package com.poc.deliverysystem.integration;

import com.poc.deliverysystem.controller.CompanyController;
import com.poc.deliverysystem.model.dto.CompanyRequestDto;
import com.poc.deliverysystem.model.dto.CompanyResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CompanyIntegrationTest {

    @LocalServerPort
    private int port;
    @Autowired
    private CompanyController companyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        assertNotNull(companyController);
    }

    @Test
    void createCompany() {
        CompanyRequestDto requestDto = new CompanyRequestDto("test 44", "12fgfdhy 67", " İzmir 35");
        CompanyResponseDto responseDto = this.restTemplate.postForObject("http://localhost:" + port + "/company/register", requestDto, CompanyResponseDto.class);
        assertNotNull(responseDto);
        assertEquals(responseDto.getUserName(), "test44");

    }

}