package com.example.myinsta.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class InsertFailException extends RuntimeException{
    private final String errorCode;
}
