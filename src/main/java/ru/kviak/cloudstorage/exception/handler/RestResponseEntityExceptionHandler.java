package ru.kviak.cloudstorage.exception.handler;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.kviak.cloudstorage.exception.*;
import ru.kviak.cloudstorage.exception.error.AppError;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {FileNotFoundException.class})
    protected ResponseEntity<?> fileNotFoundException() {
        return ResponseEntity.status(404).body(
                new AppError(404, "File not found!"));
    }

    @ExceptionHandler(value = MinioNotFoundException.class)
    protected ResponseEntity<?> minioNotFoundException() {
        return ResponseEntity.status(418).body(
                new AppError(418, "The process was interrupted through no fault of the server"));
    }

    @ExceptionHandler(value = UserAlreadyActivatedException.class)
    protected ResponseEntity<?> userAlreadyActivatedException() {
        return ResponseEntity.status(400).body(
                new AppError(400, "Account already activated!"));
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    protected ResponseEntity<?> userNotFoundException() {
        return ResponseEntity.status(404).body(
                new AppError(404, "User not found!"));
    }

    @ExceptionHandler(value =  SizeLimitExceededException.class)
    protected ResponseEntity<?> sizeLimitExceedException() {
        return ResponseEntity.badRequest().body(
                new AppError(413, "The maximum file size is exceeded!\n" +
                                                 "The maximum for regular user, size is 10 MB"));
    }
}
