package com.epam.training.ticketservice.core.account;

import com.epam.training.ticketservice.core.account.model.AccountDto;

import java.util.Optional;

public interface AccountService {
    void registerAccount(String username, String password);
    Optional<AccountDto> logIn(String username, String password);
    Optional<AccountDto> describe();
    Optional<AccountDto> logOut();
}
