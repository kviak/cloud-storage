package ru.kviak.cloudstorage.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestRegistrationUserDto {

    @Test
    public void testAllArgsConstructor() {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto("username", "password", "password", "email");

        assertEquals("username", registrationUserDto.getUsername());
        assertEquals("password", registrationUserDto.getPassword());
        assertEquals("password", registrationUserDto.getConfirmPassword());
        assertEquals("email", registrationUserDto.getEmail());
    }

    @Test
    public void testSetterAndGetter() {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto("", "", "", "");
        registrationUserDto.setUsername("username");
        registrationUserDto.setPassword("password");
        registrationUserDto.setConfirmPassword("password");
        registrationUserDto.setEmail("email");


        assertEquals("username", registrationUserDto.getUsername());
        assertEquals("password", registrationUserDto.getPassword());
        assertEquals("password", registrationUserDto.getConfirmPassword());
        assertEquals("email", registrationUserDto.getEmail());
    }
}
