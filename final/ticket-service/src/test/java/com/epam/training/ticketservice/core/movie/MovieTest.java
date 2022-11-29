package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MovieTest {
    private MovieService underTest;
    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateMovieShouldSaveObjectToRepository(){
        underTest = new MovieServiceImpl(movieRepository);
        underTest.createMovie("Test","test",10);
        Mockito.verify(movieRepository).save(new Movie("Test","test",10));
    }

    @Test
    public void testUpdateMovieShouldNotSaveIfMovieIsNotFound(){
        underTest = new MovieServiceImpl(movieRepository);
        underTest.updateMovie("Test","test",10);
        Mockito.verify(movieRepository,Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void testUpdateMovieShouldSaveUpdatedMovieIfItIsFound(){
        underTest = new MovieServiceImpl(movieRepository);
        BDDMockito.given(movieRepository.findByTitle("Test")).willReturn(Optional.of(new Movie("Test","comedy",20)));
        underTest.updateMovie("Test","test",10);
        Mockito.verify(movieRepository).save(new Movie("Test","test",10));
    }

    @Test
    public void testDeleteMovieShouldCallDeleteOnRepository(){
        underTest = new MovieServiceImpl(movieRepository);
        underTest.deleteMovie("Test");
        Mockito.verify(movieRepository).deleteMovieByTitle("Test");
    }

    @Test
    public void testGetMovieByTitleShouldReturnEmptyOptionalIfMovieIsNotFound(){
        Optional<MovieDto> expectedResult = Optional.empty();
        underTest = new MovieServiceImpl(movieRepository);
        Optional<MovieDto> actualResult = underTest.getMovieByTitle("Test");
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void testGetMovieByTitleShouldReturnTheMovieIfItIsFound(){
        Optional<MovieDto> expectedResult = Optional.of(new MovieDto("Test","comedy",20));
        BDDMockito.given(movieRepository.findByTitle("Test")).willReturn(Optional.of(new Movie("Test","comedy",20)));
        underTest = new MovieServiceImpl(movieRepository);
        Optional<MovieDto> actualResult = underTest.getMovieByTitle("Test");
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void testGetMovieByIdShouldReturnEmptyOptionalIfMovieIsNotFound(){
        Optional<MovieDto> expectedResult = Optional.empty();
        underTest = new MovieServiceImpl(movieRepository);
        Optional<MovieDto> actualResult = underTest.getMovieById(1);
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void testGetMovieByIdShouldReturnTheMovieIfItIsFound(){
        Optional<MovieDto> expectedResult = Optional.of(new MovieDto("Test","comedy",20));
        BDDMockito.given(movieRepository.findById(1)).willReturn(Optional.of(new Movie("Test","comedy",20)));
        underTest = new MovieServiceImpl(movieRepository);
        Optional<MovieDto> actualResult = underTest.getMovieById(1);
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void testGetMovieListShouldReturnWithEmptyListIfThereAreNoMovies(){
        List<MovieDto> expectedResult = Collections.emptyList();
        underTest = new MovieServiceImpl(movieRepository);
        List<MovieDto> actualResult = underTest.getMovieList();
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void testGetMovieListShouldReturnWithListOfMoviesIfThereAreAny(){
        List<MovieDto> expectedResult = Collections.singletonList(new MovieDto("Test","comedy",20));
        BDDMockito.given(movieRepository.findAll()).willReturn(Collections.singletonList(new Movie("Test","comedy",20)));
        underTest = new MovieServiceImpl(movieRepository);
        List<MovieDto> actualResult = underTest.getMovieList();
        Assertions.assertEquals(expectedResult,actualResult);
    }
}
