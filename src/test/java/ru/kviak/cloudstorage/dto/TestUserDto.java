package ru.kviak.cloudstorage.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestUserDto {
    @Test
    public void testAllArgsConstructor() {
        UserDto userDto = new UserDto(1L, "Username", "Email");

        assertEquals(1L, userDto.getId());
        assertEquals("Username", userDto.getUsername());
        assertEquals("Email", userDto.getEmail());
    }
    @Test
    public void testSetterAndGetter() {
        UserDto userDto = new UserDto(0l, "", "");

        userDto.setId(1L);
        userDto.setEmail("Email");
        userDto.setUsername("Username");

        assertEquals(1L, userDto.getId());
        assertEquals("Username", userDto.getUsername());
        assertEquals("Email", userDto.getEmail());
    }

}
