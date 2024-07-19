package org.example.e_learningback.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.RoleDto;
import org.example.e_learningback.entity.Role;
import org.example.e_learningback.entity.User;
import org.example.e_learningback.exception.RoleNotFoundException;
import org.example.e_learningback.repository.RoleRepository;
import org.example.e_learningback.repository.UserRepository;
import org.example.e_learningback.service.RoleService;
import org.example.e_learningback.utils.GenericMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final GenericMapper genericMapper;

    @Override
    public List<RoleDto> findAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return genericMapper.mapList(roles, RoleDto.class);
    }

    @Override
    public RoleDto findRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role does not exist with ID: " + id));
        return genericMapper.map(role, RoleDto.class);
    }

    @Override
    @Transactional
    public RoleDto createRole(String name) {
        Role role = new Role();
        role.setName(name);
        Role savedRole = roleRepository.save(role);
        return genericMapper.map(savedRole, RoleDto.class);
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role does not exist with ID: " + id));

        List<User> usersWithRole = role.getUsers();
        usersWithRole.forEach(user -> user.setRole(null));

        userRepository.saveAll(usersWithRole);
        roleRepository.delete(role);
    }

    @Override
    @Transactional
    public RoleDto updateRole(Long id, String name) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role with ID " + id + " not found"));

        role.setName(name);

        List<User> usersWithRole = role.getUsers();
        usersWithRole.forEach(user -> user.getRole().setName(name));

        Role savedRole = roleRepository.save(role);
        userRepository.saveAll(usersWithRole);

        return genericMapper.map(savedRole, RoleDto.class);
    }
}
