package com.example.medical_facilities.config;

import com.example.medical_facilities.dto.responses.ApiResponse;
import com.example.medical_facilities.exceptions.AuthenticationErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        AuthenticationErrorCode authenticationErrorCode = AuthenticationErrorCode.UNAUTHENTICATED;
        ObjectMapper objectMapper = new ObjectMapper();
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(authenticationErrorCode.getCode())
                .message(authenticationErrorCode.getMessage())
                .path(request.getRequestURI())
                .build();
        response.setStatus(authenticationErrorCode.getHttpStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }
}
