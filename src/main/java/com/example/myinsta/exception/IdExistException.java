package com.example.myinsta.exception;

public class IdExistException extends RuntimeException{
    private final String errorCode;
    public IdExistException(String code){
        this.errorCode=code;
    }
}

