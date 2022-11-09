package com.epam.training.ticketservice.core.account.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType type;

    public Account(String username, String password, UserType type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public enum UserType{
        NORMAL,
        ADMIN
    }
}
