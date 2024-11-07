package com.example.medical_facilities.config;

import com.example.medical_facilities.dto.requests.UserCreationRequest;
import com.example.medical_facilities.entities.RoleEntity;
import com.example.medical_facilities.entities.UserCredentialEntity;
import com.example.medical_facilities.entities.UserEntity;
import com.example.medical_facilities.entities.UserRoleEntity;
import com.example.medical_facilities.enums.RoleEnum;
import com.example.medical_facilities.exceptions.AppException;
import com.example.medical_facilities.exceptions.RoleErrorCode;
import com.example.medical_facilities.repositories.RoleRepository;
import com.example.medical_facilities.repositories.UserCredentialRepository;
import com.example.medical_facilities.repositories.UserRepository;
import com.example.medical_facilities.repositories.UserRoleRepository;
import com.example.medical_facilities.utils.UserUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class InitialConfig {
    @Autowired
    private UserCredentialRepository userCredentialRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserUtil userUtil;

    @PostConstruct
    public void initial(){
        roleInitial();
        adminInitial();
    }

    private void roleInitial(){
        for(RoleEnum role : RoleEnum.values()){
            if (!roleRepository.existsByName(role.name())){
                new RoleEntity();
                roleRepository.save(
                        RoleEntity.builder()
                                .name(role.name())
                                .build()
                );
            }
        }
    }

    private void adminInitial(){
        RoleEntity adminRole = roleRepository.findByName(RoleEnum.ADMIN.name()).orElseThrow(() -> new AppException(RoleErrorCode.NONEXISTENT_ROLE));
        Long adminId = adminRole.getId();

        if (!userRoleRepository.existsByRoleId(adminId)){
            new UserCreationRequest();
            UserCreationRequest adminCreation = UserCreationRequest.builder()
                    .email("admin@gmail.com")
                    .name("ADMIN")
                    .username("admin")
                    .password("admin")
                    .build();

            userUtil.createUserWithRole(adminCreation, RoleEnum.ADMIN.name());
        }
    }
}
