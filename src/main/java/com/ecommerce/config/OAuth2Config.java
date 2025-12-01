package com.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class OAuth2Config {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("http://localhost:3000", true)
            );
        
        return http.build();
    }
}

/*
 * Social Login Configuration:
 * 
 * Add to application.properties:
 * 
 * # Google OAuth2
 * spring.security.oauth2.client.registration.google.client-id=your-google-client-id
 * spring.security.oauth2.client.registration.google.client-secret=your-google-client-secret
 * spring.security.oauth2.client.registration.google.scope=profile,email
 * 
 * # Facebook OAuth2
 * spring.security.oauth2.client.registration.facebook.client-id=your-facebook-app-id
 * spring.security.oauth2.client.registration.facebook.client-secret=your-facebook-app-secret
 * spring.security.oauth2.client.registration.facebook.scope=public_profile,email
 * 
 * # GitHub OAuth2
 * spring.security.oauth2.client.registration.github.client-id=your-github-client-id
 * spring.security.oauth2.client.registration.github.client-secret=your-github-client-secret
 * spring.security.oauth2.client.registration.github.scope=user:email
 */