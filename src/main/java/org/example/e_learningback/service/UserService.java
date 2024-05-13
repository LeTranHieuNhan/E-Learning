package org.example.e_learningback.service;

import org.example.e_learningback.dto.UserDto;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UserDto> findAllUsers();
    UserDto findUserById(Long id);

    UserDto createUser(UserDto newUserDTO) throws IOException;

    void deleteUser(Long id);

    UserDto updateUser(Long id, UserDto newUserDTO);
}