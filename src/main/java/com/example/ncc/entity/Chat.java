package com.example.ncc.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class Chat {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sender")
    private String sender;

    @Column(name = "message")
    private String message;

    public Chat(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }
}
