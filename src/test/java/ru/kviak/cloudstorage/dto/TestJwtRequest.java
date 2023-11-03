package ru.kviak.cloudstorage.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestJwtRequest {

    @Test
    public void testAllArgsConstructor() {
        JwtRequest jwtRequest = new JwtRequest("user", "password");
        assertEquals("user", jwtRequest.getUsername());
        assertEquals("password", jwtRequest.getPassword());
    }

    @Test
    public void testGetterAndSetter() {
        JwtRequest jwtRequest = new JwtRequest("", "");

        jwtRequest.setUsername("user");
        jwtRequest.setPassword("password");

        assertEquals("user", jwtRequest.getUsername());
        assertEquals("password", jwtRequest.getPassword());
    }
}
