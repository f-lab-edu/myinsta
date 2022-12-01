package com.example.myinsta.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountsException extends RuntimeException{
    private final ErrorCode errorCode;
}
