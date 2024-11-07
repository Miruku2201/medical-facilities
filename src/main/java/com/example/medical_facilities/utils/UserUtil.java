package com.example.medical_facilities.utils;

import com.example.medical_facilities.dto.requests.UserCreationRequest;
import com.example.medical_facilities.dto.responses.UserResponse;
import com.example.medical_facilities.entities.RoleEntity;
import com.example.medical_facilities.entities.UserCredentialEntity;
import com.example.medical_facilities.entities.UserEntity;
import com.example.medical_facilities.entities.UserRoleEntity;
import com.example.medical_facilities.enums.RoleEnum;
import com.example.medical_facilities.exceptions.AppException;
import com.example.medical_facilities.exceptions.RoleErrorCode;
import com.example.medical_facilities.exceptions.UserErrorCode;
import com.example.medical_facilities.repositories.RoleRepository;
import com.example.medical_facilities.repositories.UserCredentialRepository;
import com.example.medical_facilities.repositories.UserRepository;
import com.example.medical_facilities.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Component
public class UserUtil {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserCredentialRepository userCredentialRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse createUserWithRole(UserCreationRequest request, String roleName){
        // Check username is existed
        if (userCredentialRepository.existsByUsername(request.getUsername())){
            throw new AppException(UserErrorCode.EXISTENT_USERNAME);
        }
        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        userRepository.save(user);

        UserCredentialEntity account = new UserCredentialEntity();
        account.setUsername(request.getUsername());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setUserId(user.getId());
        userCredentialRepository.save(account);

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(user.getId());
        RoleEntity roleUser = roleRepository.findByName(roleName).orElseThrow(() -> new AppException(RoleErrorCode.NONEXISTENT_ROLE));
        userRoleEntity.setRoleId(roleUser.getId());
        userRoleRepository.save(userRoleEntity);

        List<String> roleNames = userRoleRepository.findRoleNamesByUserId(user.getId());

        return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .username(account.getUsername())
                .roles(roleNames)
                .build();
    }

    public UserResponse toUserResponseFromUserEntity(UserEntity user){
        String userId = user.getId();

        UserCredentialEntity userCredentialEntity = userCredentialRepository.findByUserId(userId).orElseThrow(() -> new AppException(UserErrorCode.NONEXISTENT_USER));
        return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .username(userCredentialEntity.getUsername())
                .roles(userRoleRepository.findRoleNamesByUserId(userId))
                .build();
    }
}
