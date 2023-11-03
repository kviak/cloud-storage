package ru.kviak.cloudstorage.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.ResponseEntity;
import ru.kviak.cloudstorage.dto.JwtRequest;
import ru.kviak.cloudstorage.dto.JwtResponse;
import ru.kviak.cloudstorage.dto.RegistrationUserDto;
import ru.kviak.cloudstorage.dto.UserDto;
import ru.kviak.cloudstorage.exception.error.AppError;
import ru.kviak.cloudstorage.model.User;
import ru.kviak.cloudstorage.util.jwt.JwtTokenUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestAuthService {

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenUtils jwtTokenUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private FileMinioService fileMinioService;

    private AuthService authService;

    @BeforeEach
    public void setup() {
        authService = new AuthService(userService, jwtTokenUtils, authenticationManager, fileMinioService);
    }

    @Test
    public void testCreateAuthToken_ValidCredentials() {
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("testUser", "password")))
                .thenReturn(new UsernamePasswordAuthenticationToken("testUser", "password"));

        UserDetails userDetails = Mockito.mock(UserDetails.class);
        when(userService.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(jwtTokenUtils.generateToken(userDetails)).thenReturn("token");

        JwtRequest authRequest = new JwtRequest("testUser", "password");
        ResponseEntity<?> response = authService.createAuthToken(authRequest);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("token", ((JwtResponse) response.getBody()).getToken());
    }

    @Test
    public void testCreateAuthToken_InvalidCredentials() {
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("testUser", "invalidPassword")))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        JwtRequest authRequest = new JwtRequest("testUser", "invalidPassword");
        ResponseEntity<?> response = authService.createAuthToken(authRequest);

        assertEquals(401, response.getStatusCode().value());
        assertEquals("Неправильный логин или пароль", ((AppError) response.getBody()).getMessage());
    }

    @Test
    public void testCreateNewUser_ValidUser() {
        RegistrationUserDto userDto = new RegistrationUserDto("testUser", "password", "password", "test@example.com");
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        when(userService.createNewUser(userDto)).thenReturn(user);

        ResponseEntity<?> response = authService.createNewUser(userDto);

        assertEquals(200, response.getStatusCode().value());
        UserDto userDtoResponse = (UserDto) response.getBody();
        assertEquals(1L, userDtoResponse.getId());
        assertEquals("testUser", userDtoResponse.getUsername());
        assertEquals("test@example.com", userDtoResponse.getEmail());
    }

    @Test
    public void testCreateNewUser_PasswordMismatch() {
        RegistrationUserDto userDto = new RegistrationUserDto("testUser", "test@example.com", "password", "mismatch");

        ResponseEntity<?> response = authService.createNewUser(userDto);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Пароли не совпадают", ((AppError) response.getBody()).getMessage());
    }

    @Test
    public void testCreateNewUser_UserExists() {
        when(userService.findByUsername("existingUser")).thenReturn(Optional.of(new User()));

        RegistrationUserDto userDto = new RegistrationUserDto("existingUser", "password", "password", "test@example.com");

        ResponseEntity<?> response = authService.createNewUser(userDto);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Пользователь с указанным именем уже существует", ((AppError) response.getBody()).getMessage());
    }

}

