package com.poc.deliverysystem.service;

import com.poc.deliverysystem.model.dto.CompanyDetailResponseDto;
import com.poc.deliverysystem.model.dto.CompanyRequestDto;
import com.poc.deliverysystem.model.dto.CompanyResponseDto;
import com.poc.deliverysystem.model.entity.Company;

public interface CompanyService {

    CompanyDetailResponseDto getCompanyInfo(String companyId);

    CompanyResponseDto createNewCompany(CompanyRequestDto companyRequestDto);

    Company findCompany(String companyId);

}
