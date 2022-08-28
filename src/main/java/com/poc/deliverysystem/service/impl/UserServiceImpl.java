package com.poc.deliverysystem.service.impl;

import com.poc.deliverysystem.model.dto.UserDto;
import com.poc.deliverysystem.model.entity.CompanyUser;
import com.poc.deliverysystem.repository.UserRepository;
import com.poc.deliverysystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDto getUserByUserNameAndPassword(String userName, String password) {
        UserDto userDto = new UserDto();
        Optional<CompanyUser> optionalUser = userRepository.findByUserNameAndPassword(userName, password);
        if (optionalUser.isPresent()) {
            userDto.setUserName(optionalUser.get().getUserName());
            userDto.setPassword(optionalUser.get().getPassword());
        }
        return userDto;
    }

    @Transactional
    @Override
    public void saveUser(UserDto userDto) {
        CompanyUser companyUser = new CompanyUser();
        companyUser.setUserName(userDto.getUserName());
        companyUser.setPassword(userDto.getPassword());
        companyUser.setEnabled(true);
        companyUser.setCreationDate(LocalDateTime.now());
        userRepository.save(companyUser);
    }
}
