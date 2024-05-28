package org.example.e_learningback.controller;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.UserDto;
import org.example.e_learningback.service.JwtService;
import org.example.e_learningback.service.impl.AuthenticationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authService;
    private final JwtService jwtService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto requestDto) {

        return new ResponseEntity<>(authService.signIn(requestDto), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {

        return new ResponseEntity<>(authService.signUp(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/{token}")
    public ResponseEntity<UserDto> getUser(@PathVariable String token) {
        return new ResponseEntity<>(jwtService.findUserByToken(token),HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, authenticated user!";
    }
}
