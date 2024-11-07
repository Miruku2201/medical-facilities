package com.example.medical_facilities.services;

import com.example.medical_facilities.dto.requests.UserCreationRequest;
import com.example.medical_facilities.dto.requests.UserUpdateRequest;
import com.example.medical_facilities.dto.responses.UserResponse;
import com.example.medical_facilities.entities.UserCredentialEntity;
import com.example.medical_facilities.entities.RoleEntity;
import com.example.medical_facilities.entities.UserEntity;
import com.example.medical_facilities.entities.UserRoleEntity;
import com.example.medical_facilities.enums.RoleEnum;
import com.example.medical_facilities.exceptions.AppException;
import com.example.medical_facilities.exceptions.RoleErrorCode;
import com.example.medical_facilities.exceptions.UserErrorCode;
import com.example.medical_facilities.repositories.UserCredentialRepository;
import com.example.medical_facilities.repositories.RoleRepository;
import com.example.medical_facilities.repositories.UserRepository;
import com.example.medical_facilities.repositories.UserRoleRepository;
import com.example.medical_facilities.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCredentialRepository userCredentialRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserUtil userUtil;

    public UserResponse createUser(UserCreationRequest request){
        return userUtil.createUserWithRole(request, RoleEnum.USER.name());
    }

    public List<UserResponse> getAllUsers(){
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();
        for(UserEntity user : userEntityList){
            userResponseList.add(userUtil.toUserResponseFromUserEntity(user));
        }
        return userResponseList;
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request){
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new AppException(UserErrorCode.NONEXISTENT_USER));
        userEntity.setEmail(request.getEmail());
        userEntity.setName(request.getName());

        userRepository.save(userEntity);
        return userUtil.toUserResponseFromUserEntity(userEntity);
    }

    public Void deleteUser(String userId){
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new AppException(UserErrorCode.NONEXISTENT_USER));

        userCredentialRepository.deleteByUserId(userEntity.getId());
        userRoleRepository.deleteByUserId(userEntity.getId());
        userRepository.deleteById(userEntity.getId());
        return null;
    }
}
