package org.example.e_learningback.controller;


import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.RoleDto;
import org.example.e_learningback.dto.TeacherProfileDto;
import org.example.e_learningback.dto.TeacherReviewDto;
import org.example.e_learningback.dto.UserDto;
import org.example.e_learningback.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    List<UserDto> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto newUser
    ) throws IOException {
        return new ResponseEntity<>(userService.createUser(newUser), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto, @RequestParam Long roleId) {


        return new ResponseEntity<>(userService.updateUser(id, userDto, roleId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<TeacherProfileDto> getTeacherProfile(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getTeacherProfile(id), HttpStatus.OK);
    }

    @GetMapping("/teacher_review/{id}")
    public ResponseEntity<TeacherReviewDto> getTeacherReview(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getTeacherReview(id), HttpStatus.OK);
    }

    @PutMapping("/assgin/{roleId}/{userId}")
    public ResponseEntity<UserDto> assignRole(@PathVariable Long roleId, @PathVariable Long userId) {
        UserDto userDto = userService.assignRole(userId, roleId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}

