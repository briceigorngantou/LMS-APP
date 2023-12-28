package com.ams.users.service;

import com.ams.users.dto.UsersDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service for handling JWTs for user authentication.
 */
@Service
public class JWTService {

    /** The secret key to encrypt the JWTs with. */
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    /** The issuer the JWT is signed with. */
    @Value("${jwt.issuer}")
    private String issuer;

    /** How many seconds from generation should the JWT expire? */
    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;


    /**
     * Generates a JWT based on the given user.
     * @param user The user to generate for.
     * @return The JWT.
     */
    public String generateJWT(UsersDTO user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds)))
                .signWith(SignatureAlgorithm.HS256, algorithmKey)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(algorithmKey).parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
