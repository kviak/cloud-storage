package ru.kviak.cloudstorage.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import ru.kviak.cloudstorage.model.Role;
import ru.kviak.cloudstorage.repository.RoleRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestRoleService {

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    public void setup() {
        roleRepository = Mockito.mock(RoleRepository.class);
    }

    @Test
    public void testFindByName() {
        Role role = new Role();
        role.setId(1);
        role.setName("USER");

        when(roleRepository.findByName("USER")).thenReturn(Optional.of(role));

        Optional<Role> foundRole = roleRepository.findByName("USER");
        assertEquals(1, foundRole.get().getId());
        assertEquals("USER", foundRole.get().getName());
    }
}

