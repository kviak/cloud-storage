package ru.kviak.cloudstorage.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kviak.cloudstorage.dto.JwtRequest;
import ru.kviak.cloudstorage.dto.RegistrationUserDto;
import ru.kviak.cloudstorage.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }

    @PostMapping("/vip")
    public ResponseEntity<?> setVipRole(HttpServletRequest request) {
        if (authService.addVipRole(request)) return ResponseEntity.ok("Vip role success install.");
            else return ResponseEntity.notFound().build();
    }

    @GetMapping("/activate/{code}")
    public ResponseEntity<?> activate(@PathVariable String code){
        authService.activateUser(code);
        return ResponseEntity.ok("Account activated!");
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request){
        return ResponseEntity.ok(authService.getUserInfo(request));
    }
}
