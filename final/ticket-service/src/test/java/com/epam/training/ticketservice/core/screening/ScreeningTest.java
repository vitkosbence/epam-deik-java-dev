package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import com.epam.training.ticketservice.core.screening.exceptions.MovieOrRoomNotFoundException;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

public class ScreeningTest {
    private ScreeningService underTest;
    @Mock
    private ScreeningRepository screeningRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateScreeningShouldSaveToRepositoryIfNoViolationsAreMade(){
        underTest = new ScreeningServiceImpl(screeningRepository,movieRepository,roomRepository);
        LocalDateTime now = LocalDateTime.now();
        BDDMockito.given(roomRepository.findRoomByName("Test")).willReturn(Optional.of(new Room(1,"Test",10,10)));
        BDDMockito.given(movieRepository.findByTitle("Test")).willReturn(Optional.of(new Movie(1,"Test","comedy",20)));
        underTest.createScreening("Test","Test", now);
        Mockito.verify(screeningRepository).save(new Screening(1,1,now));
    }

    @Test
    public void testCreateScreeningShouldThrowExceptionIfMovieIsNotFound(){
        underTest = new ScreeningServiceImpl(screeningRepository,movieRepository,roomRepository);
        LocalDateTime now = LocalDateTime.now();
        BDDMockito.given(roomRepository.findRoomByName("Test")).willReturn(Optional.of(new Room(1,"Test",10,10)));
        Assertions.assertThrows(MovieOrRoomNotFoundException.class,() -> underTest.createScreening("Test","Test", now));
    }

    @Test
    public void testCreateScreeningShouldThrowExceptionIfRoomIsNotFound(){
        underTest = new ScreeningServiceImpl(screeningRepository,movieRepository,roomRepository);
        LocalDateTime now = LocalDateTime.now();
        BDDMockito.given(movieRepository.findByTitle("Test")).willReturn(Optional.of(new Movie(1,"Test","comedy",20)));
        Assertions.assertThrows(MovieOrRoomNotFoundException.class,() -> underTest.createScreening("Test","Test", now));
    }


}
