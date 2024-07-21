package org.example.e_learningback.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.*;
import org.example.e_learningback.entity.Course;
import org.example.e_learningback.entity.Role;
import org.example.e_learningback.entity.User;
import org.example.e_learningback.exception.RoleNotFoundException;
import org.example.e_learningback.exception.UserNotFoundException;
import org.example.e_learningback.repository.CourseRepository;
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
    private final CourseRepository courseRepository;

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return genericMapper.mapList(users, UserDto.class);
    }

    @Override
    public UserDto findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User does not exist with ID: " + id));
        return genericMapper.map(user, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto newUserDTO) throws IOException {
        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RoleNotFoundException("Role does not exist"));
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
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User does not exist with ID: " + id));
            List<Course> courses = user.getCourses();
            List<Course> exitCourse = courses.stream().map(course -> {
                course.setUser(null);
                return course;
            }).toList();
            courseRepository.saveAll(exitCourse);
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User does not exist with ID: " + id);
        }
    }

    @Override
    @Transactional
    public UserDto updateUser(Long id, UserDto newUserDTO , Long roleId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + roleId));

        if (newUserDTO.getName() != null) {
            user.setName(newUserDTO.getName());
        }
        if (newUserDTO.getEmail() != null) {
            user.setEmail(newUserDTO.getEmail());
        }
        if (newUserDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
        }
        if (newUserDTO.getBio() != null) {
            user.setBio(newUserDTO.getBio());
        }
        if (newUserDTO.getAvatar() != null) {
            user.setAvatar(newUserDTO.getAvatar());
        }
        if (newUserDTO.getOccupation() != null) {
            user.setOccupation(newUserDTO.getOccupation());
        }
        user.setRole(role);

        User savedUser = userRepository.save(user);
        return genericMapper.map(savedUser, UserDto.class);
    }

    @Override
    public TeacherProfileDto getTeacherProfile(Long id) {
        return userRepository.getTeacherProfile(id)
                .orElseThrow(() -> new UserNotFoundException("Teacher not found with ID: " + id));
    }

    @Override
    public TeacherReviewDto getTeacherReview(Long id) {
        return userRepository.getTeacherReview(id)
                .orElseThrow(() -> new UserNotFoundException("Teacher not found with ID: " + id));
    }

    @Override
    public UserDto assignRole(Long id, Long roleId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent()) {
            user.setRole(role.get());
            User savedUser = userRepository.save(user);
            return genericMapper.map(savedUser, UserDto.class);
        }
        throw new RoleNotFoundException("Role not found with ID: " + roleId);
    }
}
