package ru.kviak.cloudstorage.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.kviak.cloudstorage.util.jwt.JwtTokenUtils;

import java.util.Collections;

public class TestJwtRequestFilter {

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @Mock
    private JwtTokenUtils jwtTokenUtils;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoFilterInternal() throws Exception {
        String jwt = "valid_jwt_here";
        String username = "testUser";
        SecurityContextHolder.getContext().setAuthentication(null); // Ensure no existing authentication

        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        Mockito.when(jwtTokenUtils.getUsername(jwt)).thenReturn(username);
        Mockito.when(jwtTokenUtils.getRoles(jwt)).thenReturn(Collections.singletonList("ROLE_USER"));

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Assertions.assertNotNull(authenticationToken);
        Assertions.assertEquals(username, authenticationToken.getPrincipal());
        Assertions.assertTrue(authenticationToken.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));

        Mockito.verify(filterChain, Mockito.times(1)).doFilter(request, response);
    }
}

