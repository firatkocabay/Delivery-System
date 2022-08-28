package com.poc.deliverysystem.service.impl;

import com.poc.deliverysystem.exception.CompanyNotFoundException;
import com.poc.deliverysystem.model.dto.CompanyDetailResponseDto;
import com.poc.deliverysystem.model.dto.CompanyRequestDto;
import com.poc.deliverysystem.model.dto.CompanyResponseDto;
import com.poc.deliverysystem.model.entity.Company;
import com.poc.deliverysystem.repository.CompanyRepository;
import com.poc.deliverysystem.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.Normalizer;

@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.companyRepository = companyRepository;
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }

    @Override
    public CompanyDetailResponseDto getCompanyInfo(String companyId) {
        CompanyDetailResponseDto responseDto = new CompanyDetailResponseDto();
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException("Company not found!"));
        responseDto.setCompanyId(company.getCompanyId());
        responseDto.setCompanyName(company.getCompanyName());
        responseDto.setCompanyAddress(company.getCompanyAddress());
        responseDto.setCompanyWarehouseAddress(company.getCompanyWarehouseAddresses());
        return responseDto;
    }

    @Transactional
    @Override
    public CompanyResponseDto createNewCompany(CompanyRequestDto companyRequestDto) {
        CompanyResponseDto responseDto = new CompanyResponseDto();
        Company company = new Company();
        company.setCompanyName(companyRequestDto.getCompanyName());
        company.setCompanyAddress(companyRequestDto.getCompanyAddress());
        company.setCompanyWarehouseAddresses(companyRequestDto.getCompanyWarehouseAddress());
        String companyId = companyRepository.save(company).getCompanyId();
        responseDto.setCompanyId(companyId);
        String companyUserName = Normalizer.normalize(companyRequestDto.getCompanyName().toLowerCase().replace(" ",""), Normalizer.Form.NFD);
        inMemoryUserDetailsManager.createUser(User.withUsername(companyUserName).password(companyId).roles("USER").build());
        responseDto.setUserName(companyUserName);
        responseDto.setPassword(companyId);
        return responseDto;
    }

    @Override
    public Company findCompany(String companyId) {
        return companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException("Company not found!"));
    }

}
