package com.poc.deliverysystem.service;

import com.poc.deliverysystem.model.dto.UserDto;
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
        UserDto userDto = new UserDto();
        userDto.setUserName("test");
        userDto.setPassword("pass123");
        userDto.setCompanyId("abc456");

        userService.saveUser(userDto);

        UserDto expectedUserDto = userService.getUserByUserNameAndPassword(userDto.getUserName(), userDto.getPassword());

        assertEquals(expectedUserDto.getUserName(), userDto.getUserName());
        assertEquals(expectedUserDto.getPassword(), userDto.getPassword());
        assertEquals(expectedUserDto.getCompanyId(), userDto.getCompanyId());
    }

}