package com.example.medical_facilities.exceptions;

import com.example.medical_facilities.dto.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.swing.text.html.parser.Entity;

@ControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<?>> handlingAppException(AppException exception){
        IErrorCode iErrorCode = exception.getIErrorCode();

        ApiResponse<?> response = ApiResponse.builder()
                .code(iErrorCode.getCode())
                .message(iErrorCode.getMessage())
                .build();
        return ResponseEntity.status(iErrorCode.getHttpStatusCode()).body(response);
    }
}
