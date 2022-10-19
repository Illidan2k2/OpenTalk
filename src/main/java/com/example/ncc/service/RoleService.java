package com.example.ncc.service;

import com.example.ncc.Exception.RoleNotFoundException;
import com.example.ncc.dto.role.RoleDto;
import com.example.ncc.dto.user.UserDto;
import com.example.ncc.entity.Role;
import com.example.ncc.mapper.MapStructMapper;
import com.example.ncc.repository.RoleRepository;
import com.example.ncc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final MapStructMapper mapper;

    public Role addRole(RoleDto roleDto) {
        Role role = mapper.roleDtoToRole(roleDto);
        return roleRepository.save(role);
    }

    public Page<RoleDto> getAllRoleDTO(Pageable pageable) {
        return roleRepository.findAll(pageable).map(mapper::roleDto);
    }

    public RoleDto updateRole(RoleDto roleDto) {
        Role role = roleRepository.findById(roleDto.getId())
                .orElseThrow(() -> {
                    return new RoleNotFoundException("This role is not created yet!");
                });
        role.setName(roleDto.getName());
        roleRepository.save(role);
        return roleDto;
    }

    public void deleteRole(Integer id) {
        Optional<Role> role = roleRepository.findById(id);
        if(role.isEmpty()){
            throw new RoleNotFoundException("This role does not exist yet!");
        }
        roleRepository.delete(role.get());
    }
    public Page<UserDto> ViewUserByRole(Integer id, Pageable pageable){
        Optional<Role> role = roleRepository.findById(id);
        if(role.isEmpty()){
            throw new RoleNotFoundException("This role does not exist yet!");
        }
        return userRepository.findByRole(role.get(),pageable).map(mapper::userDto);
    }

    public RoleDto findRoleDtoByName(String name){
        return roleRepository.findRoleDtoByName(name);
    }
}
