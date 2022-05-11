package com.example.demo.exception;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class LibraryExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        ExceptionResponse response = createExceptionResponse(ex, ex.getStatus(), Arrays.asList(ex.getReason()), request);
        return ResponseEntity.status(ex.getStatus()).body(response);
    }
    
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse response = createExceptionResponse(ex, status, Arrays.asList(ex.getMessage()), request);
        return ResponseEntity.status(status).body(response);	
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = fieldErrors.stream().map(f -> f.getDefaultMessage()).collect(Collectors.toList());

        ExceptionResponse response = createExceptionResponse(ex, status, errors, request);
        return ResponseEntity.status(status).body(response);
    }	
        
    private ExceptionResponse createExceptionResponse(Exception ex, HttpStatus status, List<String> errors, WebRequest request) {
        return ExceptionResponse.builder()
                .timestamp(ZonedDateTime.now())
                .status(status.value()).errors(errors)
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
    }

}
