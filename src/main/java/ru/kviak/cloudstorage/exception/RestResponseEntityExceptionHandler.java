package ru.kviak.cloudstorage.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {FileNotFoundException.class})
    protected ResponseEntity<?> fileNotFoundException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "File not found!";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = MinioNotFoundException.class)
    protected ResponseEntity<?> minioNotFoundException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Error with connection!";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = UserAlreadyActivatedException.class)
    protected ResponseEntity<?> userAlreadyActivatedException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Account already activated!";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    protected ResponseEntity<?> userNotFoundException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "User not found!";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
