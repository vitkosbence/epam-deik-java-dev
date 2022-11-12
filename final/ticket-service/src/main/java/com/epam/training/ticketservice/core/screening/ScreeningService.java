package com.epam.training.ticketservice.core.screening;


import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.exceptions.MovieOrRoomNotFoundException;
import com.epam.training.ticketservice.core.screening.exceptions.ScreeningInBreaktimeException;
import com.epam.training.ticketservice.core.screening.exceptions.ScreeningOverlapsException;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningService {
    void createScreening(String movieName, String roomName, LocalDateTime startTime)
            throws ScreeningOverlapsException, ScreeningInBreaktimeException, MovieOrRoomNotFoundException;
    void deleteScreening(String movieName, String roomName, LocalDateTime startTime);
    List<ScreeningDto> getScreeningList();
}
