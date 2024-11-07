package com.example.medical_facilities.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AuthenticationErrorCode implements IErrorCode{
    UNAUTHENTICATED             (1001, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED                (1002, "Unauthorized", HttpStatus.FORBIDDEN),
    ;
    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;
}
