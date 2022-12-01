package com.example.myinsta.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_INPUT(700, "Input data is not valid"),
    ALREADY_EXIST_EMAIL(701, "Input Email is already exist"),
    FAILED_TO_INSERT(702, "Sign-up failed");

    private final int status;
    private final String message;

    }
