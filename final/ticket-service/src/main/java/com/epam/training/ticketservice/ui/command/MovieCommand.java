package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.account.AccountService;
import com.epam.training.ticketservice.core.account.model.AccountDto;
import com.epam.training.ticketservice.core.account.persistence.entity.Account;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class MovieCommand {

    private final MovieService movieService;
    private final AccountService accountService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create movie", value = "Creates a new movie from: title, genre, and length arguments")
    public String createMovie(String title,String genre, int length) {
        movieService.createMovie(title,genre,length);
        return null;
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update movie", value = "Updates am existing movie from: title, genre, and length arguments by title")
    public String updateMovie(String title,String genre, int length) {
        movieService.updateMovie(title,genre,length);
        return null;
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete movie", value = "Deletes a movie by title")
    public String deleteMovie(String title) {
        movieService.deleteMovie(title);
        return null;
    }

    @ShellMethod(key = "list movies", value = "Lists all existing movies")
    public String movieList() {
        List<MovieDto> movies = movieService.getMovieList();
        if(movies.isEmpty()){
            return "There are no movies at the moment";
        }
        StringBuilder stringBuilder = new StringBuilder();
        String separator = "";
        for (MovieDto movie:
             movies) {
            stringBuilder.append(separator+movie.getTitle()+" ("+movie.getGenre()+", "+movie.getLength()+" minutes)");
            separator = "\n";
        }
        return stringBuilder.toString();
    }

    private Availability isAvailable(){
        Optional<AccountDto> account = accountService.describe();
        return account.isPresent() && account.get().getType() == Account.UserType.ADMIN ?
                Availability.available() :
                Availability.unavailable("You are not logged in as an admin");
    }
}
