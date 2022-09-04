package com.poc.deliverysystem.configuration;

import com.poc.deliverysystem.service.basicauth.interceptor.CompanySecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
    @Autowired
    CompanySecurityInterceptor companySecurityInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(companySecurityInterceptor);
    }

}