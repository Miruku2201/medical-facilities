package com.example.medical_facilities.exceptions;

import org.springframework.http.HttpStatusCode;

public interface IErrorCode {
    int getCode();
    String getMessage();
    HttpStatusCode getHttpStatusCode();
}
