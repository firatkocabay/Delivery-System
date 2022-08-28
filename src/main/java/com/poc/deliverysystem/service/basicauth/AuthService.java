package com.poc.deliverysystem.service.basicauth;

public interface AuthService {
    Boolean validateBasicAuthentication(String basicAuthHeaderValue);

}