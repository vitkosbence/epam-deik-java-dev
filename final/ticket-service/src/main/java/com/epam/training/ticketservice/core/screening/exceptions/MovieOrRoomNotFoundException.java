package com.epam.training.ticketservice.core.screening.exceptions;

public class MovieOrRoomNotFoundException extends Exception{
    public MovieOrRoomNotFoundException(String message) {
        super(message);
    }
}
