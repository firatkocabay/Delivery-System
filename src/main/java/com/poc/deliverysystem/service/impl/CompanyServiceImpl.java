package com.poc.deliverysystem.service.impl;

import com.poc.deliverysystem.exception.CompanyNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.Base64;

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

    @Transactional
    @Override
    public CompanyResponseDto createNewCompany(CompanyRequestDto companyRequestDto) {
        CompanyResponseDto responseDto = new CompanyResponseDto();
        final String companyId = createCompany(companyRequestDto);
        String companyUserName = Normalizer.normalize(companyRequestDto.getCompanyName().toLowerCase().replace(" ",""), Normalizer.Form.NFD);
        createCompanyUser(companyId, companyUserName);
        responseDto.setUserName(companyUserName);
        responseDto.setPassword(companyId);
        return responseDto;
    }

    @Transactional
    protected String createCompany(CompanyRequestDto companyRequestDto) {
        Company company = new Company();
        company.setCompanyName(companyRequestDto.getCompanyName());
        company.setCompanyAddress(companyRequestDto.getCompanyAddress());
        company.setCompanyWarehouseAddresses(companyRequestDto.getCompanyWarehouseAddress());
        return companyRepository.save(company).getCompanyId();
    }

    private void createCompanyUser(String companyId, String companyUserName) {
        UserDto userDto = new UserDto();
        userDto.setUserName(companyUserName);
        userDto.setPassword(Base64.getEncoder().encodeToString(companyId.getBytes()));
        userDto.setCompanyId(companyId);
        userService.saveUser(userDto);
    }

    @Override
    public Company findCompany(String companyId) {
        return companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException("Company not found!"));
    }

}
