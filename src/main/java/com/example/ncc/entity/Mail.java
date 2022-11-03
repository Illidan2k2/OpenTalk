package com.example.ncc.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "mails")
@Getter
@Setter
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "mail_title")
    private String title;

    @Column(name = "message_content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "sender_id",nullable = false)
    private User sender;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_mail_details",
            inverseJoinColumns = {@JoinColumn(name = "receiver_id", referencedColumnName = "id")},
            joinColumns = {@JoinColumn(name = "mail_id", referencedColumnName = "id")}
    )
    private Set<User> receivers = new HashSet<>();
}
