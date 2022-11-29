package com.epam.training.ticketservice.core.room.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String name;
    private Integer rows;
    private Integer columns;

    public Room(String name, Integer rows, Integer columns) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }
}
