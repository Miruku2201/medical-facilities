package com.example.medical_facilities.controllers;

import com.example.medical_facilities.dto.requests.UserLoginRequest;
import com.example.medical_facilities.dto.responses.ApiResponse;
import com.example.medical_facilities.dto.responses.AuthenticationResponse;
import com.example.medical_facilities.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> login(@RequestBody UserLoginRequest request ){
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.login(request))
                .build();
    }
}
