package ru.kviak.cloudstorage.exception.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.kviak.cloudstorage.exception.error.AppError;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRestResponseEntityExceptionHandler {

    private RestResponseEntityExceptionHandler exceptionHandler;

    @BeforeEach
    public void setup() {
        exceptionHandler = new RestResponseEntityExceptionHandler();
    }

    @Test
    public void testHandleFileNotFoundException() {
        ResponseEntity<?> response = exceptionHandler.fileNotFoundException();
        assertEquals(404, response.getStatusCode().value());
        assertEquals("File not found!", ((AppError) response.getBody()).getMessage());
    }

    @Test
    public void testHandleMinioNotFoundException() {
        ResponseEntity<?> response = exceptionHandler.minioNotFoundException();
        assertEquals(418, response.getStatusCode().value());
        assertEquals("The process was interrupted through no fault of the server", ((AppError) response.getBody()).getMessage());
    }

    @Test
    public void testHandleUserAlreadyActivatedException() {
        ResponseEntity<?> response = exceptionHandler.userAlreadyActivatedException();
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Account already activated!", ((AppError) response.getBody()).getMessage());
    }

    @Test
    public void testHandleUserNotFoundException() {
        ResponseEntity<?> response = exceptionHandler.userNotFoundException();
        assertEquals(404, response.getStatusCode().value());
        assertEquals("User not found!", ((AppError) response.getBody()).getMessage());
    }

    @Test
    public void testHandleSizeLimitExceedException() {
        ResponseEntity<?> response = exceptionHandler.sizeLimitExceedException();
        assertEquals(413, response.getStatusCode().value());
        String expectedMessage = """
                The maximum file size is exceeded!
                The maximum for regular user, size is 10 MB
                The maximum for vip user, size is 20 MB""";
        assertEquals(expectedMessage, ((AppError) response.getBody()).getMessage());
    }
}

