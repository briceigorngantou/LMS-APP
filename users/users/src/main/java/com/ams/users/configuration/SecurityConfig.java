package com.ams.users.configuration;

import static org.springframework.security.config.Customizer.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        private final JWTRequestFilter jwtRequestFilter;

        public SecurityConfig(JWTRequestFilter jwtRequestFilter) {
                this.jwtRequestFilter = jwtRequestFilter;
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http.authorizeHttpRequests(auth -> {
                        auth
                                        .requestMatchers("/api/users/**")
                                        .hasAnyRole("ADMIN", "STUDENT", "INSTRUCTOR")
                                        .requestMatchers("/api/auth/register/", "/api/auth/login", "api/home/test")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated();
                })
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/").permitAll())
                                .addFilterBefore(this.jwtRequestFilter, AuthorizationFilter.class)
                                .csrf(csrf -> csrf
                                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .exceptionHandling(exception -> exception
                                                .authenticationEntryPoint(new BasicAuthenticationEntryPoint())
                                                .accessDeniedHandler(new AccessDeniedHandlerImpl()))
                                .httpBasic(withDefaults())
                                .build();
        }

        @Bean
        public InMemoryUserDetailsManager userDetailsService() {
                UserDetails user = User
                                .builder()
                                .username("student")
                                .password(passwordEncoder().encode("student"))
                                .roles("STUDENT")
                                .build();
                UserDetails admin = User
                                .builder()
                                .username("admin")
                                .password("admin")
                                .roles("ADMIN")
                                .build();
                UserDetails instructor = User
                                .builder()
                                .username("instructor")
                                .password(passwordEncoder().encode("instructor"))
                                .roles("INSTRUCTOR")
                                .build();

                return new InMemoryUserDetailsManager(user, instructor, admin);
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
