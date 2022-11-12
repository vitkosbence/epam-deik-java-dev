package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ScreeningDto {
    MovieDto movie;
    RoomDto room;
    LocalDateTime startTime;
}
