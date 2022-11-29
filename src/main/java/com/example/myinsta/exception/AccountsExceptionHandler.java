package com.example.myinsta.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Locale;

/**
 * AccountsExceptionHandler
 * Controller based Exception handling which provided by spring with @ControllerAdvice
 *
 * @ControllerAdvice
 * Make controller can interact with whole application. With @ControllerAdvice Annotation the methods in controller can interact with all the other controllers.
 * @StringBuffer
 * String has a certain condition to use StringBuilder, if the concatenation expression is constant expression, then JVM uses StringBuilder to concatenating.
 * In this case code is not using constant expression so that StringBuilder must involve to reduce a GC performance issue caused by generating new String objects.
 * Furthermore, StringBuffer is a thread-safe class while StringBuilder is a thread-unsafe class. StringBuffer achieves thread-safe by providing synchronized operations.
 * And Spring application uses threaded environment so StringBuffer is correct choice for this circumstance.
 *
 */
@ControllerAdvice
@Slf4j
public class AccountsExceptionHandler {
    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handler(MethodArgumentNotValidException e){
        List<FieldError> errors = e.getFieldErrors();
        StringBuffer json = new StringBuffer("{");
        for ( FieldError error : errors ) {
            json.append("\"errorCode:\"\"700\",\"message\":" + messageSource.getMessage("700" , null, Locale.getDefault() ) +", " );
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
