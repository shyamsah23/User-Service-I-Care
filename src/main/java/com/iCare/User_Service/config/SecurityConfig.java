package com.iCare.User_Service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())  // disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/dummy").permitAll()
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/auth/signup").permitAll()
                        .anyRequest().authenticated()   // allow others only if logged in
                )
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable()); // disable basic auth

        return httpSecurity.build();
    }

}
