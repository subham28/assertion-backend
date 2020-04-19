package com.assertion.assignment.advice;

import com.assertion.assignment.constants.ApplicationConstants;
import com.assertion.assignment.exception.PasswordGenerationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlingAdvice extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(PasswordGenerationException.class)
    public ResponseEntity<Object> handleApplicationExceptions(PasswordGenerationException ex, WebRequest webRequest){
        Map<String,Object> body =  new LinkedHashMap<>();
        body.put(ApplicationConstants.TIMESTAMP, LocalDateTime.now());
        body.put(ApplicationConstants.ERROR_CODE,ex.getErrorCode());
        body.put(ApplicationConstants.ERROR_DESC,ex.getErrorDesc());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
