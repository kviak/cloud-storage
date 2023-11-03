package ru.kviak.cloudstorage.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestUserViewDto {
    @Test
    public void testAllArgsConstructor() {
        UserViewDto userViewDto = new UserViewDto("username", "role");

        assertEquals("username", userViewDto.getUsername());
        assertEquals("role", userViewDto.getRoles());
    }
    @Test
    public void testSetterAndGetter() {
        UserViewDto userViewDto = new UserViewDto("", "");

        userViewDto.setUsername("username");
        userViewDto.setRoles("role");

        assertEquals("username", userViewDto.getUsername());
        assertEquals("role", userViewDto.getRoles());
    }
}
