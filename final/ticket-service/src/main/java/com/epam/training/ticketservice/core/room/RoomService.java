package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.model.RoomDto;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    void createRoom(String name,int rows,int columns);
    void updateRoom(String name,int rows,int columns);
    void deleteRoom(String name);
    Optional<RoomDto> getRoomByName(String name);
    Optional<RoomDto> getRoomById(Integer id);
    List<RoomDto> getRoomList();
}
