package com.poc.deliverysystem.service.impl;

import com.poc.deliverysystem.exception.CompanyNotFoundException;
import com.poc.deliverysystem.model.dto.CompanyDetailResponseDto;
import com.poc.deliverysystem.model.dto.CompanyRequestDto;
import com.poc.deliverysystem.model.dto.CompanyResponseDto;
import com.poc.deliverysystem.model.dto.UserDto;
import com.poc.deliverysystem.model.entity.Company;
import com.poc.deliverysystem.repository.CompanyRepository;
import com.poc.deliverysystem.service.CompanyService;
import com.poc.deliverysystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.Normalizer;
import java.util.Base64;
import java.util.prefs.BackingStoreException;

@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final UserService userService;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, UserService userService) {
        this.companyRepository = companyRepository;
        this.userService = userService;
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
        String companyUserName = Normalizer.normalize(companyRequestDto.getCompanyName().toLowerCase().replace(" ",""), Normalizer.Form.NFD);
        UserDto userDto = new UserDto();
        userDto.setUserName(companyUserName);
        userDto.setPassword(Base64.getEncoder().encodeToString(companyId.getBytes()));
        userService.saveUser(userDto);
        responseDto.setUserName(companyUserName);
        responseDto.setPassword(companyId);
        return responseDto;
    }

    @Override
    public Company findCompany(String companyId) {
        return companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException("Company not found!"));
    }

}
