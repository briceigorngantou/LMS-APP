package com.ams.users.service;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${app.jwttoken.message}")
    private String message;

    @Autowired
    public JWTService() {
    }

    /**
     * @param username The username information
     * @return The authentication token
     * @author Brice Ngantou
     */
    public String generateJWT(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (1000 *
                        expiryInSeconds)))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * @param token
     * @return String
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * @param token
     * @return Date
     */
    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    /**
     * @param token
     * @return Claims
     */
    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(algorithmKey).parseClaimsJws(token).getBody();
    }

    /**
     * @param token
     * @return boolean
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
