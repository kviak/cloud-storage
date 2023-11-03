package ru.kviak.cloudstorage.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kviak.cloudstorage.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestUserRepository {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository = Mockito.mock(UserRepository.class);
    }

    @Test
    public void testFindByUsername() {
        User user = new User();
        user.setId(1L);
        user.setUsername("test_user");

        when(userRepository.findByUsername("test_user")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userRepository.findByUsername("test_user");
        assertEquals(1L, foundUser.get().getId());
        assertEquals("test_user", foundUser.get().getUsername());
    }

    @Test
    public void testFindByActivationCode() {
        User user = new User();
        user.setId(2L);
        user.setActivationCode("test_activation_code");

        when(userRepository.findByActivationCode("test_activation_code")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userRepository.findByActivationCode("test_activation_code");
        assertEquals(2L, foundUser.get().getId());
        assertEquals("test_activation_code", foundUser.get().getActivationCode());
    }

    @Test
    public void testFindByEmail() {
        User user = new User();
        user.setId(3L);
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userRepository.findByEmail("test@example.com");
        assertEquals(3L, foundUser.get().getId());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }
}

