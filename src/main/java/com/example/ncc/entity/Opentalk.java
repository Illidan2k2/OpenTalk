
package com.example.ncc.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Opentalk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String link;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_opentalk_detail",
        inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        joinColumns = {@JoinColumn(name = "opentalk_id", referencedColumnName = "id")}
    )
    private Set<User> users = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "branch_id",nullable = false)
    private Branch branch;

    /*public OpentalkDto convertToDTO() {
        return OpentalkDto.builder()
                .id(this.id)
                .topic(this.topic)
                .date(this.date)
                .link(this.link)
                .branchDTO(this.branch.convertToDTO())
                .userOpentalkDto_set(this.users.stream().map(User::convertToOpentalkDTO).collect(Collectors.toSet()))
                .build();
    }*/
}