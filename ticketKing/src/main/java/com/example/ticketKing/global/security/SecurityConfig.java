package com.example.ticketKing.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(
                        formLogin -> formLogin
                                .loginPage("/usr/member/login")
                                .defaultSuccessUrl("/")
                )
                .logout(
                        logout -> logout
                                .logoutUrl("/usr/member/logout")
                )
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/usr/concert/**") // 특정 URL 패턴 설정
                                .authenticated() // 인증된 사용자만 접근 허용
                )
                .authorizeRequests()
                .anyRequest() // 나머지 모든 요청에 대해서
                .permitAll(); // 모든 사용자에게 접근 허용

        return http.build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}