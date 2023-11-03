package ru.kviak.cloudstorage.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.kviak.cloudstorage.dto.RegistrationUserDto;
import ru.kviak.cloudstorage.dto.UserDto;
import ru.kviak.cloudstorage.model.Role;
import ru.kviak.cloudstorage.model.User;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestUserMapper {

    private UserMapper userMapper;

    @BeforeEach
    public void setup() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    public void testMapToUserDto() {
        User user = new User();
        user.setUsername("john_doe");
        user.setEmail("john@example.com");
        user.setPassword("encodedPassword");
        user.setRoles(Collections.singletonList(new Role()));
        user.setActivationCode(UUID.randomUUID().toString());

        UserDto userDto = userMapper.mapTo(user);

        assertNotNull(userDto);
        assertEquals("john_doe", userDto.getUsername());
        assertEquals("john@example.com", userDto.getEmail());
    }

    @Test
    public void testMapToUser() {
        RegistrationUserDto userDto = new RegistrationUserDto("alice_smith","encodedPassword" ,"encodedPassword", "alice@example.com");
        String encodedPassword = "encodedPassword";
        List<Role> roles = Collections.singletonList(new Role());

        User user = userMapper.mapTo(userDto, encodedPassword, roles);

        assertNotNull(user);
        assertEquals("alice_smith", user.getUsername());
        assertEquals("alice@example.com", user.getEmail());
        assertEquals(encodedPassword, user.getPassword());
    }
}

