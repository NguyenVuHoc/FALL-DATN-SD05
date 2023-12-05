package com.example.befall23datnsd05.security.admin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(2)
@EnableWebSecurity
public class AdminSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomNhanVienDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider providerAdmin(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }
    @Bean
    public SecurityFilterChain filterChainAdmin(HttpSecurity http) throws Exception{
        http.authenticationProvider(providerAdmin());
        http.authorizeHttpRequests(
                rq ->
                        rq.requestMatchers("/").permitAll()
                                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                                .requestMatchers("/admin**").authenticated()
        )
                .formLogin(
                        f-> f.loginPage("/")
                                .usernameParameter("email")
                                .loginProcessingUrl("/admin/login")
                                .defaultSuccessUrl("/admin/ban-hang")
                                .permitAll()
                )
        ;
        return http.build();
    }
}
