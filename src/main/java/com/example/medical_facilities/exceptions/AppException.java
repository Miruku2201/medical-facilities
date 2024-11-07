package com.example.medical_facilities.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {
    private IErrorCode iErrorCode;

    public AppException(IErrorCode iErrorCode){
        super(iErrorCode.getMessage());
        this.iErrorCode = iErrorCode;
    }

    public AppException(IErrorCode iErrorCode, Throwable cause){
        super(iErrorCode.getMessage(), cause);
        this.iErrorCode = iErrorCode;
    }
}
