package org.example.e_learningback.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.RoleDto;
import org.example.e_learningback.dto.UserDto;
import org.example.e_learningback.entity.Role;
import org.example.e_learningback.entity.User;
import org.example.e_learningback.repository.RoleRepository;
import org.example.e_learningback.repository.UserRepository;
import org.example.e_learningback.service.UserService;
import org.example.e_learningback.utils.GenericMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GenericMapper genericMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return genericMapper.mapList(users, UserDto.class);
    }


    @Override
    public UserDto findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new RuntimeException("User does not exist");
        }

        return genericMapper.map(user.get(), UserDto.class);
    }


    @Override
    @Transactional
    public UserDto createUser(UserDto newUserDTO) throws IOException {
//        newUserDTO.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
        Role role = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role does not exit "));
        User user = genericMapper.map(newUserDTO, User.class);


        if (user.getRole() == null) {
            user.setRole(new Role());
        }

        user.setRole(role);
        user.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));

        User savedUser = userRepository.save(user);
        UserDto map = genericMapper.map(savedUser, UserDto.class);
        map.setRole(genericMapper.map(role, RoleDto.class));

        return map;
    }


    @Override
    @Transactional
    public void deleteUser(Long id) {
        boolean isExist = userRepository.existsById(id);

        if (isExist) {
            userRepository.deleteById(id);
        } else
            throw new RuntimeException("User doesnt exit with ID " + id);
    }


    @Override
    @Transactional
    public UserDto updateUser(Long id, UserDto newUserDTO) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            User existingUser = user.get();

            if (newUserDTO.getName() != null) {
                existingUser.setName(newUserDTO.getName());
            }
            if (newUserDTO.getEmail() != null) {
                existingUser.setEmail(newUserDTO.getEmail());
            }
            if (newUserDTO.getPassword() != null) {
                existingUser.setPassword(newUserDTO.getPassword());
            }

            User savedUser = userRepository.save(existingUser);

            return genericMapper.map(savedUser, UserDto.class);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }
}
