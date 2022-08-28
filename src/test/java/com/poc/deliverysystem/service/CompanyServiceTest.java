package com.poc.deliverysystem.service;

import com.poc.deliverysystem.exception.CompanyNotFoundException;
import com.poc.deliverysystem.model.dto.CompanyRequestDto;
import com.poc.deliverysystem.model.dto.CompanyResponseDto;
import com.poc.deliverysystem.model.entity.Company;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.Charset;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    @Test
    void contextLoads() {
        assertNotNull(companyService);
    }

    @Test
    void givenCompanyRequestWhenCallCreateNewCompanyThenReturnCompanyResponse() {
        CompanyRequestDto requestDto = new CompanyRequestDto("xyz786", "abc 12345", "567 tre");
        CompanyResponseDto newCompany = companyService.createNewCompany(requestDto);

        assertNotNull(newCompany);
        assertEquals(newCompany.getUserName(), requestDto.getCompanyName());
    }

    @Test
    void givenCompanyRequestWhenCallCreateCompanyFindCompanyThenShouldReturnSameCompany() {
        CompanyRequestDto requestDto = new CompanyRequestDto("abc 123", "exy 12345", "123 test fsd");
        CompanyResponseDto responseDto = companyService.createNewCompany(requestDto);
        Company company = companyService.findCompany(responseDto.getPassword());

        assertNotNull(company);
        assertEquals(company.getCompanyName(), requestDto.getCompanyName());
        assertEquals(company.getCompanyId(), responseDto.getPassword());
    }

    @Test
    void givenRandomCompanyIdWhenCallFindCompanyThenReturnCompanyNotFound() {
        CompanyNotFoundException exception = Assertions.assertThrows(CompanyNotFoundException.class, () -> {
            byte[] array = new byte[7];
            new Random().nextBytes(array);
            companyService.findCompany(new String(array, Charset.forName("UTF-8")));
        });
        assertNotNull(exception);
        assertEquals(exception.getMessage(), "Company not found!");
    }

}