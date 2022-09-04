package com.poc.deliverysystem.service;

import com.poc.deliverysystem.model.dto.CompanyUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        assertNotNull(userService);
    }

    @Test
    void givenUserDtoWhenCallSaveUserThenReturnedDtoShouldBeSame() {
        CompanyUserDto companyUserDto = new CompanyUserDto();
        companyUserDto.setUserName("test");
        companyUserDto.setPassword("pass123");
        companyUserDto.setCompanyId("abc456");

        userService.saveUser(companyUserDto);

        CompanyUserDto expectedCompanyUserDto = userService.getUserByUserNameAndPassword(companyUserDto.getUserName(), companyUserDto.getPassword());

        assertEquals(expectedCompanyUserDto.getUserName(), companyUserDto.getUserName());
        assertEquals(expectedCompanyUserDto.getPassword(), companyUserDto.getPassword());
        assertEquals(expectedCompanyUserDto.getCompanyId(), companyUserDto.getCompanyId());
    }

}