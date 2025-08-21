package com.servico.agenda.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servico.agenda.config.AuthenticationService;
import com.servico.agenda.dto.TokenDTO;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationManager authenticationManager,
                          AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.username(), loginRequest.password()
            )
        );

        String jwt = authenticationService.authenticate(authentication);

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccessToken(jwt);
        tokenDTO.setRefreshToken(jwt);
        tokenDTO.setUsername(authentication.getName());
        tokenDTO.setAuthenticated(authentication.isAuthenticated());
        //tokenDTO.setCreated(Instant.now());
        //tokenDTO.setExpiration(Instant.now().plusSeconds(3600));

        return ResponseEntity.ok(tokenDTO);
    }

    public record LoginRequest(String username, String password) {}
}
