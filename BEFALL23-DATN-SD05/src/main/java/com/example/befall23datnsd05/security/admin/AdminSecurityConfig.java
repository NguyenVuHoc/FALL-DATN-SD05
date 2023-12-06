package com.example.befall23datnsd05.security.admin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@Order(1)
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
        http.authenticationProvider(providerAdmin());
        http.authorizeHttpRequests(
                        rq ->
                                rq.requestMatchers("/admin/login").permitAll()
                                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                )
                .formLogin(
                        f -> f.loginPage("/admin/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .loginProcessingUrl("/admin/login")
                                .defaultSuccessUrl("/admin/ban-hang")
                                .permitAll()
                )
                .logout(
                        lo-> lo.logoutUrl("/admin/logout").logoutSuccessUrl("/admin/login")
                )
                .exceptionHandling(
                    ex -> ex.accessDeniedHandler(accessDeniedHandler())
                )
        ;
//        http.authenticationProvider(providerAdmin());
//
//        http.authorizeRequests().requestMatchers("/").permitAll();
//
//        http.antMatcher("/admin/**")
//                .authorizeRequests().anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/admin/login")
//                .usernameParameter("email")
//                .loginProcessingUrl("/admin/login")
//                .defaultSuccessUrl("/admin/ban-hang")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/admin/logout")
//                .logoutSuccessUrl("/admin/login");

        return http.build();
    }

}
