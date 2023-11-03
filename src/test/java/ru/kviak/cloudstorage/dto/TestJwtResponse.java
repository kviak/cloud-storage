package ru.kviak.cloudstorage.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestJwtResponse {

    @Test
    public void testAllArgsConstructor() {
        JwtResponse jwtResponse = new JwtResponse("token");

        assertEquals("token", jwtResponse.getToken());
    }

    @Test
    public void testGetterAndSetter() {
        JwtResponse jwtResponse = new JwtResponse("");

        jwtResponse.setToken("token");

        assertEquals("token", jwtResponse.getToken());
    }
}
