package com.epam.training.ticketservice.core.movie.model;

import lombok.Value;

@Value
public class MovieDto {
    String title;
    String genre;
    int length;
}
