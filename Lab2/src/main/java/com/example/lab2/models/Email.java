package com.example.lab2.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table
@NoArgsConstructor
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "body")
    private String body;

    @Column(name = "recipient")
    private String recipient;

    public Email(String body, String recipient) {
        this.body = body;
        this.recipient = recipient;
    }
}
