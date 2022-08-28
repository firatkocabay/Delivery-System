package com.poc.deliverysystem.controller;

import com.poc.deliverysystem.model.dto.CompanyDetailResponseDto;
import com.poc.deliverysystem.model.dto.CompanyRequestDto;
import com.poc.deliverysystem.model.dto.CompanyResponseDto;
import com.poc.deliverysystem.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/v1/company/{companyId}")
    public ResponseEntity<CompanyDetailResponseDto> getCompany(@PathVariable String companyId) {
        return new ResponseEntity<>(companyService.getCompanyInfo(companyId), HttpStatus.OK);
    }

    @PostMapping("/company/register")
    public ResponseEntity<CompanyResponseDto> createCompany(@RequestBody CompanyRequestDto companyRequestDto) {
        return new ResponseEntity<>(companyService.createNewCompany(companyRequestDto), HttpStatus.OK);
    }

}
