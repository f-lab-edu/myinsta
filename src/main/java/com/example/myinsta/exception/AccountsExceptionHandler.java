package com.example.myinsta.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * AccountsExceptionHandler
 * Controller based Exception handling which provided by spring with @ControllerAdvice
 *
 * @ControllerAdvice
 *
 */
@ControllerAdvice
public class AccountsExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handler(MethodArgumentNotValidException e){
        List<FieldError> errors = e.getFieldErrors();
        String json = "{";
        for ( FieldError error : errors) {
            json += "\"" + error.getField() + "\":";
            json += "\"" + error.getRejectedValue() + "\"";
            json += ", \"message\":";
            json += "\"" + error.getDefaultMessage() + "\"";
            if(errors.size() >= 2){
                json += ", ";
            }
        }
        json += "}";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(json);
    }
}
