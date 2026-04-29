package com.preventhub.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                // 1. On force Spring Security à utiliser notre Bean CORS ci-dessous
                .cors(Customizer.withDefaults())
                
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authorizeExchange(exchange -> exchange
                        // 2. On laisse expressément passer les requêtes "Preflight" (OPTIONS)
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        // 3. On laisse passer le reste pour le moment
                        .anyExchange().permitAll()
                )
                .build();
    }

    // 4. LE BEAN MAGIQUE : Il donne les autorisations exactes à Spring Security
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // On autorise tes deux ports Frontend
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:4301"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // On applique ces règles sur toutes les routes de l'API (/**)
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}