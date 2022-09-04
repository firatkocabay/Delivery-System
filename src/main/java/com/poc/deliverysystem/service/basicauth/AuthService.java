package com.poc.deliverysystem.service.basicauth;

public interface AuthService {
    boolean validateBasicAuthentication(String basicAuthHeaderValue);

}