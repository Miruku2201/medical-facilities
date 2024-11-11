package com.example.medical_facilities.services;

import com.example.medical_facilities.dto.requests.UserCreationRequest;
import com.example.medical_facilities.dto.requests.UserLoginRequest;
import com.example.medical_facilities.dto.responses.AuthenticationResponse;
import com.example.medical_facilities.entities.UserCredentialEntity;
import com.example.medical_facilities.entities.UserEntity;
import com.example.medical_facilities.entities.UserRoleEntity;
import com.example.medical_facilities.exceptions.AppException;
import com.example.medical_facilities.exceptions.UserErrorCode;
import com.example.medical_facilities.repositories.UserCredentialRepository;
import com.example.medical_facilities.repositories.UserRepository;
import com.example.medical_facilities.repositories.UserRoleRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Value("${jwt.signer-key}")
    private String signerKey;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private JwtService jwtService;

    private List<String> buildRole(String userId){
        List<String> listRoleNames = userRoleRepository.findRoleNamesByUserId(userId);
        return listRoleNames.stream().map(role -> "ROLE_"+role).toList();
    }

    public AuthenticationResponse login (UserLoginRequest request){
        // Check authenticated
        UserCredentialEntity userCredential = userCredentialRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(UserErrorCode.NONEXISTENT_USERNAME));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isMatchedPassword = passwordEncoder.matches(request.getPassword(), userCredential.getPassword());

        if(!isMatchedPassword){
            throw new AppException(UserErrorCode.INCORRECT_PASSWORD);
        }

        // Generate Token for user login
        UserEntity user = userRepository.findById(userCredential.getUserId()).orElseThrow(() -> new AppException(UserErrorCode.NONEXISTENT_USER));

        return AuthenticationResponse.builder()
                .accessToken(jwtService.getAccessToken(user))
                .build();
    }
}
