package com.poc.deliverysystem.controller;

import com.poc.deliverysystem.model.dto.CompanyRequestDto;
import com.poc.deliverysystem.model.dto.CompanyResponseDto;
import com.poc.deliverysystem.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/company/register")
    public ResponseEntity<CompanyResponseDto> createCompany(@RequestBody CompanyRequestDto companyRequestDto) {
        return new ResponseEntity<>(companyService.createNewCompany(companyRequestDto), HttpStatus.OK);
    }

}
