package com.example.myinsta.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * AccountsExceptionHandler
 * Controller based Exception handling which provided by spring with @ControllerAdvice
 *
 * @ControllerAdvice Make controller can interact with whole application. With @ControllerAdvice Annotation the methods in controller can interact with all the other controllers.
 * @StringBuffer String has a certain condition to use StringBuilder, if the concatenation expression is constant expression, then JVM uses StringBuilder to concatenating.
 * In this case code is not using constant expression so that StringBuilder must involve to reduce a GC performance issue caused by generating new String objects.
 * Furthermore, StringBuffer is a thread-safe class while StringBuilder is a thread-unsafe class. StringBuffer achieves thread-safe by providing synchronized operations.
 * And Spring application uses threaded environment so StringBuffer is correct choice for this circumstance.
 * @handler Handling MethodArgumentNotValidException
 * Returns ErrorResponse with error code and error message
 * If there are multiple invalid input, then return one from errors
 * The return FieldError has priority as below, the highest priority value will be returned.
 * 1. Email
 * 2. Nick Name
 * 3. Password
 */
@ControllerAdvice
@Slf4j
public class AccountsExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handler(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getFieldErrors();
        List<ErrorResponse> errorResponse = new ArrayList<>();
        for (FieldError error : errors) {
            errorResponse.add(ErrorResponse.builder().errorCode(ErrorCode.INVALID_INPUT.getStatus()).errorMessage(error.getDefaultMessage()).build());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorResponse.toArray()[0]);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> errors = e.getConstraintViolations();
        List<ErrorResponse> errorResponse = new ArrayList<>();
        for (ConstraintViolation error : errors) {
            errorResponse.add(ErrorResponse.builder().errorCode(ErrorCode.INVALID_INPUT.getStatus()).errorMessage(error.getMessage()).build());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorResponse.toArray()[0]);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handler(MethodArgumentTypeMismatchException e) {
        ErrorResponse errorResponse = ErrorResponse.builder().errorCode(ErrorCode.INVALID_INPUT.getStatus()).errorMessage("Wrong type").build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> CustomExceptionHandler(CustomException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(e.getErrorCode().getStatus())
                .errorMessage(e.getErrorCode().getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }
}
