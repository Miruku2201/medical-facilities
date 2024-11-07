package com.example.medical_facilities.config;


import com.example.medical_facilities.dto.responses.ApiResponse;
import com.example.medical_facilities.exceptions.AuthenticationErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //* This handler happens after user sign in with his/her account. That means authenticated step was befall.
        //* That errors because of not authorized
        AuthenticationErrorCode authenticationErrorCode = AuthenticationErrorCode.UNAUTHORIZED;
        ObjectMapper objectMapper = new ObjectMapper();
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(authenticationErrorCode.getCode())
                .message(authenticationErrorCode.getMessage())
                .path(request.getRequestURI())
                .build();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }
}
