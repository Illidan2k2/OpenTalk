
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
@Table(name = "opentalks")
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
        name = "user_opentalk_details",
        inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        joinColumns = {@JoinColumn(name = "opentalk_id", referencedColumnName = "id")}
    )
    private Set<User> users = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "opentalk_branch",nullable = false)
    private Branch branch;

}