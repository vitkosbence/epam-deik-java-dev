package com.epam.training.ticketservice.core.screening.exceptions;

public class MovieOrRoomNotFoundException extends RuntimeException{
    public MovieOrRoomNotFoundException(String message) {
        super(message);
    }
}
