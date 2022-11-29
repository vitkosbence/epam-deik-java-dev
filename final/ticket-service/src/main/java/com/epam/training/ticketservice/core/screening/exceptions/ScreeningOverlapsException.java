package com.epam.training.ticketservice.core.screening.exceptions;

public class ScreeningOverlapsException extends RuntimeException{
    public ScreeningOverlapsException(String message) {
        super(message);
    }
}
