package com.epam.training.ticketservice.core.configuration;

import com.epam.training.ticketservice.core.account.persistence.entity.Account;
import com.epam.training.ticketservice.core.account.persistence.repository.AccountRepository;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InMemoryDatabaseInitializer {
    private final AccountRepository accountRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    @PostConstruct
    public void init() {
        Account admin = new Account("admin", "admin", Account.UserType.ADMIN);
        accountRepository.save(admin);
    }
}
