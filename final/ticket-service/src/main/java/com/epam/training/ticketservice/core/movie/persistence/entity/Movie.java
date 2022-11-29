package com.epam.training.ticketservice.core.movie.persistence.entity;

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
public class Movie {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String title;
    private String genre;
    private Integer length;

    public Movie(String title, String genre, Integer length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
    }
}
