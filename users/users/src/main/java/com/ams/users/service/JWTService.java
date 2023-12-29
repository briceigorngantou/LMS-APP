package com.ams.users.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ams.users.dto.LoginBody;
import com.ams.users.repository.UsersRepository;

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

    private UsersRepository usersRepository;

    @Autowired
    public JWTService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Map<String, String> generateToken(LoginBody user) {
        String jwtToken = "";
        jwtToken = Jwts.builder().setSubject(user.getUsername()).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secret").compact();
        Map<String, String> jwtTokenGen = new HashMap<>();
        jwtTokenGen.put("token", jwtToken);
        jwtTokenGen.put("message", message);
        return jwtTokenGen;
    }

    public String generateJWT(LoginBody user) {
        // return Jwts.builder()
        // .setSubject(user.getUsername())
        // .setIssuedAt(new Date())
        // .setExpiration(new Date(System.currentTimeMillis() + (1000 *
        // expiryInSeconds)))
        // .signWith(SignatureAlgorithm.HS256, algorithmKey)
        // .compact();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .signWith(secretKey, SignatureAlgorithm.HS256)
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
