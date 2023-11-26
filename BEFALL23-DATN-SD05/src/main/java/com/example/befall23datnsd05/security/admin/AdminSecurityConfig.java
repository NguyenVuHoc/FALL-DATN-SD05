package com.example.befall23datnsd05.security.admin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AdminSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(req -> req.requestMatchers("/").permitAll());
        return http.build();
    }
}
