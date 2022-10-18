package com.example.ncc.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "branch_name")
    private String name;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "branch",cascade = CascadeType.ALL)
    private Set<Opentalk> opentalks = new HashSet<>();

    /*public BranchDto convertToDTO(){
        BranchDto branchDTO = new BranchDto();
        branchDTO.setId(this.id);
        branchDTO.setName(this.name);
        return branchDTO;
    }*/

    @Override
    public String toString() {
        return "Branch{" +
                "name=" + name +
                '}';
    }
}
