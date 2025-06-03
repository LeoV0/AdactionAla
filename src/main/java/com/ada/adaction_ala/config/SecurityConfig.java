package com.ada.adaction_ala.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/volunteers/import", "/volunteers/**").permitAll()
            .anyRequest().authenticated()
        )
        .csrf(csrf -> csrf.disable())
        .httpBasic(httpBasic -> httpBasic.disable());
    return http.build();
}
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//     http
//         .authorizeHttpRequests(authorize -> authorize
//             .anyRequest().permitAll() // Autoriser toutes les requêtes
//         )
//         .csrf(csrf -> csrf.disable())
//         .httpBasic(httpBasic -> httpBasic.disable());
//     return http.build();
// }
}