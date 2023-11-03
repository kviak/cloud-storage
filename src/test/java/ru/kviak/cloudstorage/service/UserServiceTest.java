package ru.kviak.cloudstorage.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kviak.cloudstorage.dto.RegistrationUserDto;
import ru.kviak.cloudstorage.mapper.UserMapper;
import ru.kviak.cloudstorage.model.Role;
import ru.kviak.cloudstorage.model.User;
import ru.kviak.cloudstorage.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private EmailSenderService mailSender;

    @Mock
    private UserMapper userMapper;

    private UserService userService;

    @BeforeEach
    public void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        roleService = Mockito.mock(RoleService.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        mailSender = Mockito.mock(EmailSenderService.class);
        userMapper = Mockito.mock(UserMapper.class);

        userService = new UserService(userRepository, roleService, passwordEncoder, mailSender, userMapper, jdbcTemplate);
    }

    @Test
    public void testLoadUserByUsername_UserFoundAndActivated() {
        User user = new User();
        user.setUsername("john_doe");
        user.setPassword("encodedPassword");
        Role userRole = new Role();
        userRole.setId(1);
        userRole.setName("USER");
        user.setRoles(Collections.singletonList(userRole));
        user.setActivated(true);

        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername("john_doe");

        assertNotNull(userDetails);
        assertEquals("john_doe", userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("USER", userDetails.getAuthorities().iterator().next().getAuthority());
    }
}
