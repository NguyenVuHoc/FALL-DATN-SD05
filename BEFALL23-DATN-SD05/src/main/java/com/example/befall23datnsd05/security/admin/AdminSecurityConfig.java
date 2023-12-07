package com.example.befall23datnsd05.security.admin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class AdminSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomNhanVienDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider providerAdmin() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }



    @Bean
    public SecurityFilterChain filterChainAdmin(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        rq ->
                                rq.requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                                        .requestMatchers("/**").permitAll()
                )
                .formLogin(
                        f -> f.loginPage("/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/default")
                )
                .logout(
                        lo-> lo.logoutUrl("/logout").logoutSuccessUrl("/login")
                )
                .authenticationProvider(providerAdmin())
                .exceptionHandling(
                    ex -> ex.accessDeniedHandler(accessDeniedHandler())
                )
        ;
        return http.build();
    }

}
