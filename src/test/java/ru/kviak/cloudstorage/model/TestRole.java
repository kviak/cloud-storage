package ru.kviak.cloudstorage.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestRole {

    @Test
    public void testRoleEntityProperties() {
        Role role = new Role();
        role.setId(1);
        role.setName("USER");

        assertEquals(1, role.getId());
        assertEquals("USER", role.getName());
    }

    @Test
    public void testRoleEntityDefaultConstructor() {
        Role role = new Role();

        assertNull(role.getId());
        assertNull(role.getName());
    }
}
