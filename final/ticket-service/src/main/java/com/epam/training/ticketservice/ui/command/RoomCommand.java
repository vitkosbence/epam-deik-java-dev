package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.account.AccountService;
import com.epam.training.ticketservice.core.account.model.AccountDto;
import com.epam.training.ticketservice.core.account.persistence.entity.Account;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class RoomCommand {

    private final RoomService roomService;
    private final AccountService accountService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create room", value = "Creates a new room from: name, number of rows and columns arguments")
    public String createRoom(String name, int rows, int columns) {
        roomService.createRoom(name, rows, columns);
        return null;
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update room", value = "Updates room based on: name, number of rows and columns arguments")
    public String updateRoom(String name, int rows, int columns) {
        roomService.updateRoom(name, rows, columns);
        return null;
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete room", value = "Deletes room based on: name argument")
    public String deleteRoom(String name) {
        roomService.deleteRoom(name);
        return null;
    }

    @ShellMethod(key = "list rooms", value = "Lists all existing rooms")
    public String roomList() {
        List<RoomDto> rooms = roomService.getRoomList();
        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        }
        StringBuilder stringBuilder = new StringBuilder();
        String separator = "";
        for (RoomDto room :
                rooms) {
            stringBuilder.append(separator + "Room " + room.getName() + " with " + room.getRows() * room.getColumns())
                    .append(" seats, " + room.getRows() + " rows and " + room.getColumns() + " columns");
            separator = "\n";
        }
        return stringBuilder.toString();
    }

    private Availability isAvailable() {
        Optional<AccountDto> account = accountService.describe();
        return account.isPresent() && account.get().getType() == Account.UserType.ADMIN
                ? Availability.available() :
                Availability.unavailable("You are not logged in as an admin");
    }
}
