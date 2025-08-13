package com.dohimenezg.auth.service;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.function.Function;
import io.jsonwebtoken.Claims;

public interface IJwtService {
    
    String extractUsername(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);

}
