package com.example.myinsta.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_INPUT(700, "Information is not valid"),
    ALREADY_EXIST_EMAIL(701, "Email is already exist"),
    FAILED_TO_INSERT(702, "Sign-up failed"),
    FAILED_TO_INSERT_POST(801, "Post creation failed");
    private final int status;
    private final String message;
}
