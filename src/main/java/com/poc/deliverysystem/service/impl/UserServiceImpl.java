package com.poc.deliverysystem.service.impl;

import com.poc.deliverysystem.model.dto.UserDto;
import com.poc.deliverysystem.model.entity.CompanyUser;
import com.poc.deliverysystem.repository.UserRepository;
import com.poc.deliverysystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
            userDto.setCompanyId(optionalUser.get().getCompanyId());
        }
        return userDto;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(UserDto userDto) {
        CompanyUser companyUser = new CompanyUser();
        companyUser.setUserName(userDto.getUserName());
        companyUser.setPassword(userDto.getPassword());
        companyUser.setCompanyId(userDto.getCompanyId());
        companyUser.setEnabled(true);
        companyUser.setCreationDate(LocalDateTime.now());
        userRepository.save(companyUser);
    }

}
