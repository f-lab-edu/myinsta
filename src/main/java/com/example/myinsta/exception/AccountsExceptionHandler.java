package com.example.myinsta.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * AccountsExceptionHandler
 * Controller based Exception handling which provided by spring with @ControllerAdvice
 *
 * @ControllerAdvice Make controller can interact with whole application. With @ControllerAdvice Annotation the methods in controller can interact with all the other controllers.
 * @StringBuffer String has a certain condition to use StringBuilder, if the concatenation expression is constant expression, then JVM uses StringBuilder to concatenating.
 * In this case code is not using constant expression so that StringBuilder must involve to reduce a GC performance issue caused by generating new String objects.
 * Furthermore, StringBuffer is a thread-safe class while StringBuilder is a thread-unsafe class. StringBuffer achieves thread-safe by providing synchronized operations.
 * And Spring application uses threaded environment so StringBuffer is correct choice for this circumstance.
 */
@ControllerAdvice
public class AccountsExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handler(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getFieldErrors();
        List<ErrorResponse> errorResponse = new ArrayList<>();
        for (FieldError error : errors) {
            errorResponse.add(ErrorResponse.builder().errorCode(700).errorMessage(error.getRejectedValue() + ", " + error.getDefaultMessage()).build());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }

    @ExceptionHandler(AccountsException.class)
    public ResponseEntity<Object> generalExceptionHandler(AccountsException e) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(e.getErrorCode().getStatus())
                .errorMessage(e.getErrorCode().getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }
}
