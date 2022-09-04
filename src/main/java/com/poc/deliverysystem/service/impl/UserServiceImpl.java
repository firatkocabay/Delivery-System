package com.poc.deliverysystem.service.impl;

import com.poc.deliverysystem.model.dto.CompanyUserDto;
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
    public CompanyUserDto getUserByUserNameAndPassword(String userName, String password) {
        CompanyUserDto companyUserDto = new CompanyUserDto();
        Optional<CompanyUser> optionalUser = userRepository.findByUserNameAndPassword(userName, password);
        if (optionalUser.isPresent()) {
            companyUserDto.setUserName(optionalUser.get().getUserName());
            companyUserDto.setPassword(optionalUser.get().getPassword());
            companyUserDto.setCompanyId(optionalUser.get().getCompanyId());
        }
        return companyUserDto;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(CompanyUserDto companyUserDto) {
        CompanyUser companyUser = new CompanyUser();
        companyUser.setUserName(companyUserDto.getUserName());
        companyUser.setPassword(companyUserDto.getPassword());
        companyUser.setCompanyId(companyUserDto.getCompanyId());
        companyUser.setEnabled(true);
        companyUser.setCreationDate(LocalDateTime.now());
        userRepository.save(companyUser);
    }

}
