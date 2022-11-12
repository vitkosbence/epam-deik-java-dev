package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.account.AccountService;
import com.epam.training.ticketservice.core.account.model.AccountDto;
import com.epam.training.ticketservice.core.account.persistence.entity.Account;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.exceptions.MovieOrRoomNotFoundException;
import com.epam.training.ticketservice.core.screening.exceptions.ScreeningInBreaktimeException;
import com.epam.training.ticketservice.core.screening.exceptions.ScreeningOverlapsException;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class ScreeningCommand {

    private final ScreeningService screeningService;
    private final AccountService accountService;
    private final ScreeningRepository screeningRepository;
    private final RoomRepository roomRepository;
    private final MovieService movieService;
    private final RoomService roomService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create screening", value = "Creates a new room from: name, number of rows and columns arguments")
    public String createScreening(String movieTitle, String roomName, String startTimeString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(startTimeString,dateTimeFormatter);
        try{
            screeningService.createScreening(movieTitle,roomName,startTime);
        }
        catch (MovieOrRoomNotFoundException e){
            return e.getMessage();
        }
        catch (ScreeningOverlapsException e){
            return "There is an overlapping screening";
        }
        catch (ScreeningInBreaktimeException e){
            return "This would start in the break period after another screening in this room";
        }
        return null;
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete screening", value = "Creates a new room from: name, number of rows and columns arguments")
    public String deleteScreening(String movieTitle, String roomName, String startTimeString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(startTimeString,dateTimeFormatter);
        screeningService.deleteScreening(movieTitle,roomName,startTime);
        return null;
    }

    @ShellMethod(key = "list screenings", value = "Creates a new room from: name, number of rows and columns arguments")
    public String screeningList() {
        List<ScreeningDto> screenings = screeningService.getScreeningList();
        if(screenings.isEmpty()){
            return "There are no screenings";
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder stringBuilder = new StringBuilder();
        String separator = "";
        for (ScreeningDto screening:
                screenings) {
            stringBuilder.append(separator+screening.getMovie().getTitle()+" ("+screening.getMovie().getGenre())
                    .append(", "+screening.getMovie().getLength()+" minutes), screened in room ")
                    .append(screening.getRoom().getName()+", at "+screening.getStartTime().format(dateTimeFormatter));
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
