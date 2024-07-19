package org.example.e_learningback.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.JwtResponse;
import org.example.e_learningback.dto.RoleDto;
import org.example.e_learningback.dto.UserDto;
import org.example.e_learningback.entity.Role;
import org.example.e_learningback.entity.User;
import org.example.e_learningback.exception.CustomNotFoundException;
import org.example.e_learningback.exception.EmailAlreadyExistsException;
import org.example.e_learningback.repository.RoleRepository;
import org.example.e_learningback.repository.UserRepository;
import org.example.e_learningback.service.JwtService;
import org.example.e_learningback.utils.GenericMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final GenericMapper genericMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    @Transactional
    public JwtResponse signUp(UserDto newUser) {
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already registered: " + newUser.getEmail());
        }

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        Role role = roleRepository.findByName("USER").orElseThrow(() -> new CustomNotFoundException("Role does not exist"));
        User user = genericMapper.map(newUser, User.class);

        // Set the user's role
        user.setRole(role);

        User savedUser = userRepository.save(user);
        UserDto mappedUser = genericMapper.map(savedUser, UserDto.class);
        mappedUser.setRole(genericMapper.map(role, RoleDto.class));

        // Generate JWT token for the newly registered user
        String jwt = jwtService.generateToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), savedUser);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(jwt);
        jwtResponse.setRefreshToken(refreshToken);

        return jwtResponse;
    }

    @Override
    @Transactional
    public JwtResponse signIn(UserDto userDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
        System.out.println("cook");
        User user = userRepository.findByEmail(userDto.getEmail()).orElseThrow(() -> new CustomNotFoundException("User not found with email address " + userDto.getEmail()));
        // Generate JWT token for the signed-in user
        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(jwt);
        jwtResponse.setRefreshToken(refreshToken);
        return jwtResponse;
    }
}
