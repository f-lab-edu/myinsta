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
 * @StringBuffer
 * String has a certain condition to use StringBuilder, if the concatenation expression is constant expression, then JVM uses StringBuilder to concatenating.
 * In this case code is not using constant expression so that StringBuilder must involve to reduce a GC performance issue caused by generating new String objects.
 * Furthermore, StringBuffer is a thread-safe class while StringBuilder is a thread-unsafe class. StringBuffer achieves thread-safe by providing synchronized operations.
 * And Spring application uses threaded environment so StringBuffer is correct choice for this circumstance.
 *
 */
@ControllerAdvice
public class AccountsExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handler(MethodArgumentNotValidException e){
        List<FieldError> errors = e.getFieldErrors();
        StringBuffer json = new StringBuffer("{");
        for ( FieldError error : errors ) {
            json.append("\"" + error.getField() + "\":");
            json.append("\"" + error.getRejectedValue() + "\"");
            json.append(", \"message\":");
            json.append("\"" + error.getDefaultMessage() + "\"");
            if(errors.size() >= 2){
                json.append(", ");
            }
        }
        json.append("}");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(json);
    }
}
