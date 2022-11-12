package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import com.epam.training.ticketservice.core.screening.exceptions.MovieOrRoomNotFoundException;
import com.epam.training.ticketservice.core.screening.exceptions.ScreeningInBreaktimeException;
import com.epam.training.ticketservice.core.screening.exceptions.ScreeningOverlapsException;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ScreeningServiceImpl implements ScreeningService{

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    @Override
    public void createScreening(String movieName, String roomName, LocalDateTime startTime)
            throws ScreeningOverlapsException, ScreeningInBreaktimeException, MovieOrRoomNotFoundException {
        Optional<Movie> movie = movieRepository.findByTitle(movieName);
        Optional<Room> room = roomRepository.findRoomByName(roomName);
        if(movie.isPresent() && room.isPresent()){
            Optional<Long> timeBefore = minutesFromLastScreening(room.get().getId(),startTime);
            Optional<Long> timeAfter = minutesUntilNextScreening(room.get().getId(),startTime,movie.get().getLength());
            if((timeBefore.isPresent() && timeBefore.get() <= 0) || (timeAfter.isPresent() && timeAfter.get() <= 0)){
                throw new ScreeningOverlapsException("The screening overlaps with another");
            }
            if((timeBefore.isPresent() && timeBefore.get() < 10) || (timeAfter.isPresent() && timeAfter.get() < 10)){
                throw new ScreeningInBreaktimeException("The screening doesn't allow for the necessary 10 minute break time");
            }
            Screening screening = new Screening(movie.get().getId(),room.get().getId(),startTime);
            screeningRepository.save(screening);
        }
        else{
            throw new MovieOrRoomNotFoundException("Either the given movie or room doesn't exist.");
        }
    }

    private Optional<Long> minutesFromLastScreening(Integer roomId, LocalDateTime startTime){
        Optional<Screening> lastScreening = screeningRepository
                .getFirstByStartTimeIsLessThanEqualAndRoomIdEquals(startTime,roomId);
        if(lastScreening.isEmpty()){
            return Optional.empty();
        }
        Optional<Movie> lastMovie = movieRepository.findById(lastScreening.get().getMovieId());
        if(lastMovie.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(lastScreening.get().getStartTime().until(startTime, ChronoUnit.MINUTES)-lastMovie.get().getLength());
    }

    private Optional<Long> minutesUntilNextScreening(Integer roomId, LocalDateTime startTime, int movieLength){
        Optional<Screening> lastScreening = screeningRepository
                .getFirstByStartTimeIsGreaterThanEqualAndRoomIdEquals(startTime,roomId);
        if(lastScreening.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(startTime.until(lastScreening.get().getStartTime(),ChronoUnit.MINUTES)-movieLength);
    }

    @Override
    public void deleteScreening(String movieName, String roomName, LocalDateTime startTime) {
        Optional<Movie> movie = movieRepository.findByTitle(movieName);
        Optional<Room> room = roomRepository.findRoomByName(roomName);
        if(movie.isPresent() && room.isPresent()){
            screeningRepository.deleteScreeningByMovieIdAndRoomIdAndStartTime(movie.get().getId()
                    ,room.get().getId()
                    ,startTime);
        }
    }

    @Override
    public List<ScreeningDto> getScreeningList() {
        return screeningRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    private ScreeningDto convertEntityToDto(Screening screening){
        Optional<Movie> movie = movieRepository.findById(screening.getMovieId());
        Optional<Room> room = roomRepository.findById(screening.getRoomId());
        if(movie.isEmpty() || room.isEmpty()){
            return null;
        }
        return new ScreeningDto(new MovieDto(movie.get().getTitle(),movie.get().getGenre(),movie.get().getLength())
                ,new RoomDto(room.get().getName(),room.get().getRows(),room.get().getColumns())
                ,screening.getStartTime());
    }

    private Optional<ScreeningDto> convertEntityToDto(Optional<Screening> screening){
        return screening.map(this::convertEntityToDto);
    }
}
