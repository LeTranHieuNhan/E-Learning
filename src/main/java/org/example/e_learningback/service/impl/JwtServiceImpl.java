package org.example.e_learningback.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.UserDto;
import org.example.e_learningback.entity.User;
import org.example.e_learningback.repository.UserRepository;
import org.example.e_learningback.service.JwtService;

import org.example.e_learningback.utils.GenericMapper;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {
    private final UserRepository userRepository;
    private final GenericMapper genericMapper;

    public String generateToken(User userDetails) {
        long expirationMillis = System.currentTimeMillis() + (30 * 24 * 60 * 60 * 1000L); // 30 days in milliseconds

        return Jwts.builder()
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expirationMillis)) // Set expiration to maximum possible value
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private <T> T extractClaim(String token, Function<Claims, T> required) {
        final Claims claims = extractAllClaims(token);
        return required.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignKey() {
        byte[] key = Decoders.BASE64.decode("413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
        return Keys.hmacShaKeyFor(key);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String token, User userDetails) {
        final String username = extractEmail(token);
        return (username.equals(userDetails.getEmail()) && !isTokenExpired(token));
    }

    @Override
    public String generateRefreshToken(Map<String, Object> extractClaims, User userDetails) {

        return Jwts.builder().
                setClaims(extractClaims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000000 * 60 * 60 * 10))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public UserDto findUserByToken(String token) {
        String email = extractEmail(token);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return genericMapper.map(user, UserDto.class);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
