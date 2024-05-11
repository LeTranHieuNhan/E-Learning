package org.example.e_learningback.controller;


import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.RoleDto;
import org.example.e_learningback.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("")
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        List<RoleDto> roles = roleService.findAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        RoleDto roleDto = roleService.findRoleById(id);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestParam String name) {
        RoleDto createdRoleDto = roleService.createRole(name);
        return new ResponseEntity<>(createdRoleDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable Long id, @RequestParam String name) {
        RoleDto updatedRoleDto = roleService.updateRole(id, name);
        return new ResponseEntity<>(updatedRoleDto, HttpStatus.OK);
    }
}
