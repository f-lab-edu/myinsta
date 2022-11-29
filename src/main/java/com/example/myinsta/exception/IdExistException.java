package com.example.myinsta.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IdExistException extends RuntimeException{
    private final String errorCode;
}

