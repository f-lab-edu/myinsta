package com.example.myinsta.exception;

public class IdExistException extends RuntimeException{
    public IdExistException(String message){
        super(message);
    }
}

