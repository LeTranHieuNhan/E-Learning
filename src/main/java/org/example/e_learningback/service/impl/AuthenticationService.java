package org.example.e_learningback.service.impl;

import org.example.e_learningback.dto.JwtResponse;
import org.example.e_learningback.dto.UserDto;

public interface AuthenticationService {
    JwtResponse signUp(UserDto signUpRequest);
    JwtResponse signIn(UserDto signInRequest);
}
