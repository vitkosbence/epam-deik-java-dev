package com.epam.training.ticketservice.core.configuration;

import com.epam.training.ticketservice.core.account.persistence.entity.Account;
import com.epam.training.ticketservice.core.account.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InMemoryDatabaseInitializer {
    private final AccountRepository accountRepository;

    @PostConstruct
    public void init(){
        Account admin = new Account("admin","admin",Account.UserType.ADMIN);
        accountRepository.save(admin);
    }
}
