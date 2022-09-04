package com.poc.deliverysystem.service;

import com.poc.deliverysystem.model.dto.CompanyUserDto;

public interface UserService {

    CompanyUserDto getUserByUserNameAndPassword(String userName, String password);

    void saveUser(CompanyUserDto companyUserDto);

}
