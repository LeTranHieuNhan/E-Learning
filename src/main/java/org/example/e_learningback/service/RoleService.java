package org.example.e_learningback.service;

import org.example.e_learningback.dto.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> findAllRoles();
    RoleDto findRoleById(Long id);
    RoleDto createRole(String name);
    void deleteRole(Long id);
    RoleDto updateRole(Long id, String name);
}
