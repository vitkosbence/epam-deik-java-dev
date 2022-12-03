package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public void createRoom(String name, int rows, int columns) {
        Room room = new Room(name, rows, columns);
        roomRepository.save(room);
    }

    @Override
    public void updateRoom(String name, int rows, int columns) {
        Optional<Room> room = roomRepository.findRoomByName(name);
        if (room.isPresent()) {
            room.get().setRows(rows);
            room.get().setColumns(columns);
            roomRepository.save(room.get());
        }
    }

    @Override
    public void deleteRoom(String name) {
        roomRepository.deleteRoomByName(name);
    }

    @Override
    public Optional<RoomDto> getRoomByName(String name) {
        return convertEntityToDto(roomRepository.findRoomByName(name));
    }

    @Override
    public Optional<RoomDto> getRoomById(Integer id) {
        return convertEntityToDto(roomRepository.findById(id));
    }

    @Override
    public List<RoomDto> getRoomList() {
        return roomRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    private RoomDto convertEntityToDto(Room room) {
        return new RoomDto(room.getName(), room.getRows(), room.getColumns());
    }

    private Optional<RoomDto> convertEntityToDto(Optional<Room> room) {
        return room.map(this::convertEntityToDto);
    }
}
