package com.ecommerce.backend.Security.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component

public class JwtUtil {

    //Generate a key manually using a secure random generator
    private static final String secretKeyString = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded().toString();

    //Generate Token
    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return CreateToken(claims, username);
            }
        
    private String CreateToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims) 
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Key getSigninKey() {
        byte[] keybytes = Decoders.BASE64.decode(secretKeyString);
        return Keys.hmacShaKeyFor(keybytes);
    }

    public String extractUserName(String token){
        return extractClaims(token, Claims::getSubject);
    }
        
    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
                
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
            }
        
    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

