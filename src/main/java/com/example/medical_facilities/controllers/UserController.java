package com.example.medical_facilities.controllers;

import com.example.medical_facilities.dto.requests.UserCreationRequest;
import com.example.medical_facilities.dto.requests.UserUpdateRequest;
import com.example.medical_facilities.dto.responses.ApiResponse;
import com.example.medical_facilities.dto.responses.UserResponse;
import com.example.medical_facilities.entities.UserEntity;
import com.example.medical_facilities.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody UserCreationRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers(){
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<Void> deleteUser(@PathVariable("userId") String userId){
        return ApiResponse.<Void>builder()
                .result(userService.deleteUser(userId))
                .message("User with id " + userId + " has been delete")
                .build();
    }
}
