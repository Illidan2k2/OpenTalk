package com.example.ncc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @JsonIgnoreProperties("role")
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<User> users;

    /*public RoleDto convertToDTO(){
        RoleDto roleDTO = new RoleDto();
        roleDTO.setId(this.id);
        roleDTO.setName(this.name);
        return roleDTO;
    }*/
}
