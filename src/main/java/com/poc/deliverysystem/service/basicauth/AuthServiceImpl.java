package com.poc.deliverysystem.service.basicauth;

import com.poc.deliverysystem.model.dto.UserDto;
import com.poc.deliverysystem.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean validateBasicAuthentication(String basicAuthHeaderValue) {
        if (StringUtils.hasText(basicAuthHeaderValue) && basicAuthHeaderValue.toLowerCase().startsWith("basic")) {
            String base64Credentials = basicAuthHeaderValue.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            final String[] values = credentials.split(":", 2);
            if (values.length == 2) {
                String username = values[0];
                String password = values[1];
                UserDto userDto = userService.getUserByUserNameAndPassword(username, Base64.getEncoder().encodeToString(password.getBytes()));
                return userDto.getUserName() != null;
            }
        }
        return false;
    }

}
