package ru.kviak.cloudstorage.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.kviak.cloudstorage.dto.UserViewDto;
import ru.kviak.cloudstorage.service.AuthService;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestAuthController {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;


    @Test
    public void testSetVipRole() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(authService.addVipRole(request)).thenReturn(true);

        ResponseEntity<?> responseEntity = authController.setVipRole(request);

        Mockito.verify(authService, Mockito.times(1)).addVipRole(request);
    }

    @Test
    public void testActivate() {
        String activationCode = "someActivationCode";

        ResponseEntity<?> responseEntity = authController.activate(activationCode);

        Mockito.verify(authService, Mockito.times(1)).activateUser(activationCode);
    }

    @Test
    public void testGetUserInfo() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(authService.getUserInfo(request)).thenReturn(new UserViewDto("user", "role"));

        ResponseEntity<?> responseEntity = authController.getUserInfo(request);

        Mockito.verify(authService, Mockito.times(1)).getUserInfo(request);
    }
}

