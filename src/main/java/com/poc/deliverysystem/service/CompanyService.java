package com.poc.deliverysystem.service;

import com.poc.deliverysystem.model.dto.CompanyRequestDto;
import com.poc.deliverysystem.model.dto.CompanyResponseDto;
import com.poc.deliverysystem.model.entity.Company;

public interface CompanyService {

    CompanyResponseDto createNewCompany(CompanyRequestDto companyRequestDto);

    Company findCompany(String companyId);

}
