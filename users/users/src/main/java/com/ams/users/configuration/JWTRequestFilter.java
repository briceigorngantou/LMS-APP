package com.ams.users.configuration;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ams.users.dto.UsersDTO;
import com.ams.users.entity.Users;
import com.ams.users.repository.UsersRepository;
import com.ams.users.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter for decoding a JWT in the Authorization header and loading the user
 * object into the authentication context.
 */
@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    /** The JWT Service. */
    private JWTService jwtService;
    
    private UsersRepository userRepository;

    /**
     * Constructor for spring injection.
     * @param jwtService
     * @param userRepository
     */
    public JWTRequestFilter(JWTService jwtService, UsersRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        UsernamePasswordAuthenticationToken token = checkToken(tokenHeader);
        if (token != null) {
            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Method to authenticate a token and return the Authentication object
     * written to the spring security context.
     * @param token The token to test.
     * @return The Authentication object if set.
     */
    private UsernamePasswordAuthenticationToken checkToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                String username = jwtService.extractUsername(token);
                Users opUser = this.userRepository.findByUsername(username);
                if (opUser.isPersisted()) {
                    UsersDTO user = new UsersDTO(opUser);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user, null, new ArrayList());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return authentication;
                }
            } catch (JwtException ex) {
            }
        }
        SecurityContextHolder.getContext().setAuthentication(null);
        return null;
    }
}
