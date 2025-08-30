package com.servico.agenda.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.servico.agenda.config.AuthenticationService;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService; 

    @InjectMocks
    private AuthenticationService authService; 

    @Test
    void testAuthenticateSuccess() {
        String username = "testuser";
        String password = "password123";
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username, password);

        when(jwtService.generateToken(any())).thenReturn("fake-jwt-token");

        String authenticatedToken = authService.authenticate(token);

        assertNotNull(authenticatedToken);
        assertEquals("fake-jwt-token", authenticatedToken);
        verify(jwtService, times(1)).generateToken(any());
    }

}
