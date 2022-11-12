package com.epam.training.ticketservice.core.room.model;

import lombok.Value;

@Value
public class RoomDto {
    String name;
    int rows;
    int columns;
}
