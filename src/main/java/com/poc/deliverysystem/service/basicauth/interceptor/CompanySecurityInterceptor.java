package com.poc.deliverysystem.service.basicauth.interceptor;

import com.poc.deliverysystem.service.basicauth.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class CompanySecurityInterceptor implements HandlerInterceptor {
    private static final String HEADER_PARAMETER_AUTHORIZATION = "authorization";
    private final AuthService authService;

    public CompanySecurityInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean isValidBasicAuthRequest = false;
        if (request.getRequestURI().contains("/company/register")
                || request.getRequestURI().contains("/h2-console"))
            return true;
        log.info("[Inside PRE Handle interceptor][" + request + "]" + "[" + request.getMethod() + "]" + request.getRequestURI());
        try {
            String basicAuthHeaderValue = request.getHeader(HEADER_PARAMETER_AUTHORIZATION);
            isValidBasicAuthRequest = authService.validateBasicAuthentication(basicAuthHeaderValue);
            if (!isValidBasicAuthRequest) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        } catch (Exception e) {
            log.error("Error occured while authenticating request : " + e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        return isValidBasicAuthRequest;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
        log.info("[Inside POST Handle Interceptor]" + request.getRequestURI());
    }

}
