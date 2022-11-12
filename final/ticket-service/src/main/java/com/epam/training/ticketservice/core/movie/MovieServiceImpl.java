package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    @Override
    public void createMovie(String title, String genre, int length) {
        Movie movie = new Movie(title,genre,length);
        movieRepository.save(movie);
    }

    @Override
    public void updateMovie(String title, String genre, int length) {
        Optional<Movie> movie =  movieRepository.findByTitle(title);
        if(movie.isPresent()){
            movie.get().setGenre(genre);
            movie.get().setLength(length);
            movieRepository.save(movie.get());
        }
    }

    @Override
    public void deleteMovie(String title) {
        movieRepository.deleteMovieByTitle(title);
    }

    @Override
    public Optional<MovieDto> getMovieByTitle(String title) {
        return convertEntityToDto(movieRepository.findByTitle(title));
    }

    @Override
    public Optional<MovieDto> getMovieById(Integer id) {
        return convertEntityToDto(movieRepository.findById(id));
    }

    @Override
    public List<MovieDto> getMovieList() {
        return movieRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    private MovieDto convertEntityToDto(Movie movie){
        return new MovieDto(movie.getTitle(), movie.getGenre(), movie.getLength());
    }

    private Optional<MovieDto> convertEntityToDto(Optional<Movie> movie){
        return movie.map(this::convertEntityToDto);
    }
}
