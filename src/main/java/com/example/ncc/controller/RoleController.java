package com.example.ncc.controller;

import com.example.ncc.dto.role.RoleDto;
import com.example.ncc.dto.user.UserDto;
import com.example.ncc.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/add_role")
    public ResponseEntity<String> addRole(@RequestBody RoleDto roleDto) {
        roleService.addRole(roleDto);
        return ResponseEntity.ok().body("Role added successfully!");
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/roleDTO_index")
    public ResponseEntity<Page<RoleDto>> getAllRoleDTO(@PageableDefault(size = 3) Pageable pageable) {
        return ResponseEntity.ok().body(roleService.getAllRoleDTO(pageable));
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/update_role")
    public ResponseEntity<String> updateRole(@RequestBody RoleDto roleDto) {
        roleService.updateRole(roleDto);
        return ResponseEntity.ok().body("Role updated successfully!");
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/remove_role/{id}")
    public ResponseEntity<String> removeRole(@PathVariable int id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok().body("Role removed successfully!");
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/view_user_by_role/{id}")
    public ResponseEntity<Page<UserDto>> ViewUserByRole(@PathVariable int id, Pageable pageable){
        return ResponseEntity.ok().body(roleService.ViewUserByRole(id,pageable));
    }

}
