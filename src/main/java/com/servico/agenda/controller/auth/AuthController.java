package com.servico.agenda.controller.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servico.agenda.dto.TokenDTO;
import com.servico.agenda.jwt.JwtTokenProvider;
import com.servico.agenda.model.User;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password);

        Authentication authentication = authenticationManager.authenticate(authToken);

        User user = (User) authentication.getPrincipal();
        String accessToken = jwtTokenProvider.generateToken(user);

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setUsername(user.getUsername());
        tokenDTO.setAuthenticated(true);
        tokenDTO.setCreated(new Date());
        tokenDTO.setExpiration(jwtTokenProvider.getExpirationDate(accessToken));
        tokenDTO.setAccessToken(accessToken);

        return ResponseEntity.ok(tokenDTO);
    }

    public record LoginRequest(String username, String password) {
	}
}
