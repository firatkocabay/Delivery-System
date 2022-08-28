package com.poc.deliverysystem.service;

import com.poc.deliverysystem.model.dto.UserDto;

public interface UserService {

    UserDto getUserByUserNameAndPassword(String userName, String password);

    void saveUser(UserDto userDto);

}
