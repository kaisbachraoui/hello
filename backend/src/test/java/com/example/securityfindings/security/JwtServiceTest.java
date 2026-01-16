package com.example.securityfindings.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.jupiter.api.Test;

class JwtServiceTest {
    @Test
    void createAndParseToken() {
        JwtService jwtService = new JwtService("test-secret-key-that-is-at-least-32-bytes", 3600);
        String token = jwtService.createToken("admin", Set.of("ADMIN"));

        var claims = jwtService.parseToken(token);
        assertThat(claims.getSubject()).isEqualTo("admin");
        assertThat(claims.get("roles")).isNotNull();
    }
}
