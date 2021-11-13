package com.boba.bobabuddy.infrastructure.handler;

import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Resource;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request){
        return handleExceptionInternal(ex, ex.getMessage(),new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = DifferentResourceException.class)
    protected ResponseEntity<Object> handleDifferentResource(RuntimeException ex, WebRequest request){
        return handleExceptionInternal(ex, ex.getMessage(),new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = DuplicateResourceException.class)
    protected ResponseEntity<Object> HandleDuplicate(RuntimeException ex, WebRequest request){
        return handleExceptionInternal(ex, ex.getMessage(),new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
