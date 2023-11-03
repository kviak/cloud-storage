package ru.kviak.cloudstorage.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestUser {

    @Test
    public void testUserEntityProperties() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john_doe");
        user.setPassword("encodedPassword");
        user.setEmail("john@example.com");
        user.setActivationCode("activationCode");
        user.setActivated(true);
        Role role = new Role();
        role.setId(1);
        role.setName("USER");
        user.setRoles(Arrays.asList(role));

        assertEquals(1L, user.getId());
        assertEquals("john_doe", user.getUsername());
        assertEquals("encodedPassword", user.getPassword());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("activationCode", user.getActivationCode());
        assertTrue(user.isActivated());
        assertNotNull(user.getRoles());
        assertEquals(1, user.getRoles().size());
        assertEquals("USER", user.getRoles().iterator().next().getName());
    }

    @Test
    public void testUserEntityDefaultConstructor() {
        User user = new User();

        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getEmail());
        assertNull(user.getActivationCode());
        assertFalse(user.isActivated());
        assertNull(user.getRoles());
    }
}

