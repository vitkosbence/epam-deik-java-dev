package com.epam.training.ticketservice.core.screening.persistence.entity;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Screening {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer movieId;
    private Integer roomId;
    private LocalDateTime startTime;

    public Screening(Integer movieId, Integer roomId, LocalDateTime startTime) {
        this.movieId = movieId;
        this.roomId = roomId;
        this.startTime = startTime;
    }
}
