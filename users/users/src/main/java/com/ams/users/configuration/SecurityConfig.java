package com.ams.users.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.access.intercept.AuthorizationFilter;


import static  org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    private JWTRequestFilter jwtRequestFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.
                authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/", "/login**", "/webjars/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated();
                })
                .oauth2Login(withDefaults())
                .logout(logout -> logout
                        .logoutSuccessUrl("/").permitAll()
                )
                .addFilterBefore(jwtRequestFilter, AuthorizationFilter.class)
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new BasicAuthenticationEntryPoint())
                        .accessDeniedHandler(new AccessDeniedHandlerImpl())
                )
                .build();
    }
}
