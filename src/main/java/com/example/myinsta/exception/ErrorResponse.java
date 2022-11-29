package com.example.myinsta.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private final String errorCode;
    private final String errorMessage;
}
