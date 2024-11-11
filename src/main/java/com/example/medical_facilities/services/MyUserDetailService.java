package com.example.medical_facilities.services;

import com.example.medical_facilities.entities.RoleEntity;
import com.example.medical_facilities.entities.UserCredentialEntity;
import com.example.medical_facilities.entities.UserDetailEntity;
import com.example.medical_facilities.exceptions.AppException;
import com.example.medical_facilities.exceptions.UserErrorCode;
import com.example.medical_facilities.repositories.UserCredentialRepository;
import com.example.medical_facilities.repositories.UserRepository;
import com.example.medical_facilities.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserCredentialRepository userCredentialRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredentialEntity userCredential = userCredentialRepository.findByUsername(username).orElseThrow(() -> new AppException(UserErrorCode.NONEXISTENT_USERNAME));
        List<RoleEntity> listRoles = userRoleRepository.findRoleByUserId(userCredential.getUserId());
        return new UserDetailEntity(userCredential, listRoles);
    }
}
