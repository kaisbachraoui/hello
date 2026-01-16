package com.example.securityfindings.service;

import com.example.securityfindings.dto.AuthRequest;
import com.example.securityfindings.dto.AuthResponse;
import com.example.securityfindings.repository.UserAccountRepository;
import com.example.securityfindings.security.JwtService;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserAccountRepository userAccountRepository;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager,
                       UserAccountRepository userAccountRepository,
                       JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userAccountRepository = userAccountRepository;
        this.jwtService = jwtService;
    }

    public AuthResponse login(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
        } catch (AuthenticationException ex) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        var user = userAccountRepository.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        Set<String> roles = user.getRoles().stream().map(Enum::name).collect(Collectors.toSet());
        String token = jwtService.createToken(user.getUsername(), roles);
        return new AuthResponse(token, user.getUsername(), roles);
    }
}
