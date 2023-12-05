package com.example.befall23datnsd05.security.admin;

import com.example.befall23datnsd05.security.customer.CustomKhachHangDetailService;
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
    public UserDetailsService userDetailsServiceCustomer(){
        return new CustomKhachHangDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoderCustomer(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider providerCustomer(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceCustomer());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoderCustomer());
        return daoAuthenticationProvider;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChainAdmin(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(
                rq -> {
                    rq.requestMatchers("/admin").permitAll()
                            .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                            .requestMatchers("/admin/**").authenticated();
                })
                .formLogin(
                        f-> f.loginPage("/admin")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .loginProcessingUrl("/admin/login")
                                .defaultSuccessUrl("/admin/ban-hang")
                                .permitAll()
                )
                .authenticationProvider(providerAdmin())
        ;
//        http.authorizeHttpRequests(
//                        rq ->
//                                rq.requestMatchers("/wingman", "/wingman/dang-ky").permitAll()
//                                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
//                                        .requestMatchers("/wingman/**").authenticated()
//                )
//                .formLogin(
//                        f-> f.loginPage("/wingman")
//                                .usernameParameter("email")
//                                .loginProcessingUrl("/user/login")
//                                .defaultSuccessUrl("/wingman/trang-chu")
//                                .permitAll()
//                )
//                .authenticationProvider(providerCustomer())
//        ;
        return http.build();
    }

}
