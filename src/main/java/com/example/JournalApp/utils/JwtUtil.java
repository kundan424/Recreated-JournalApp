package com.example.JournalApp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private String SECRET_KEY = "";

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String token){
        Claims claims = extractAllclaims(token);
        return claims.getSubject();
    }

    public Date extractExpiration(String token){
        return extractAllclaims(token).getExpiration();
    }

    private Claims extractAllclaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(String username){
        Map<String , Object> claims = new HashMap<>();
        return createToken(claims , username);
    }


    private String createToken(Map<String , Object> claims , String subject){
    return Jwts.builder().
            claims(claims).
            subject(subject)
            .header().empty().add("typ" , "JWT")
            .and()
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
            .signWith(getSigningKey())
            .compact();
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token){
        return !isTokenExpired(token);
    }
}
