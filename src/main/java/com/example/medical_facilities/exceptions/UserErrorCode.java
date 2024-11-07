package com.example.medical_facilities.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum UserErrorCode implements IErrorCode{
    NONEXISTENT_USER(3001, "User is not created", HttpStatus.BAD_REQUEST),
    EXISTENT_USERNAME(3002, "Username is existed", HttpStatus.BAD_REQUEST),
    NONEXISTENT_USERNAME(3003, "Username is not existed", HttpStatus.BAD_REQUEST),
    INCORRECT_PASSWORD(3004, "Password is incorrect", HttpStatus.BAD_REQUEST),
    ;
    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;
}
