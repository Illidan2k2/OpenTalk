package com.example.ncc.repository;

import com.example.ncc.dto.role.RoleDto;
import com.example.ncc.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query(value = "SELECT new com.example.ncc.dto.role.RoleDto(r.id,r.name) FROM Role r WHERE r.name = :name")
    RoleDto findRoleDtoByName(@Param("name") String name);
}
