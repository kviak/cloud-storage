package ru.kviak.cloudstorage.exception.error;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestAppError {
    @Test
    public void testAllArgsConstructor() {
        AppError appError = new AppError(404, "Message");

        assertEquals(404, appError.getStatus());
        assertEquals("Message", appError.getMessage());
    }

    @Test
    public void testSetterAndGetter() {
        AppError appError = new AppError(0, "");

        appError.setStatus(404);
        appError.setMessage("Message");

        assertEquals(404, appError.getStatus());
        assertEquals("Message", appError.getMessage());
    }
}
