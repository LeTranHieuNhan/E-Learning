package org.example.e_learningback.service;

import org.example.e_learningback.dto.UserDto;
import org.example.e_learningback.entity.User;

import java.util.Map;

public interface JwtService {
    String generateToken(User userDetails);
    String extractEmail(String token);
    boolean isTokenValid(String token, User userDetails);

    String generateRefreshToken(Map<String,Object> extractClaims, User userDetails);
    UserDto findUserByToken(String token);
}
