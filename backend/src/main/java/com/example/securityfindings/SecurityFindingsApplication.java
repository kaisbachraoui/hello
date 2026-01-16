package com.example.securityfindings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SecurityFindingsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityFindingsApplication.class, args);
    }
}
