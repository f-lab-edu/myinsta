package com.example.myinsta.security;

import com.example.myinsta.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class JwtExceptionHandler {
    @ExceptionHandler({JwtCustomException.class})
    public ResponseEntity<Object> handleCustomException(JwtCustomException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(e.getHttpStatus().value())
                .errorMessage(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

}
