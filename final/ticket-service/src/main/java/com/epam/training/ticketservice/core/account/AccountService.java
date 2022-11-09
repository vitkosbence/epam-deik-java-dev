package com.epam.training.ticketservice.core.account;

import com.epam.training.ticketservice.core.account.model.AccountDto;

import java.util.Optional;

public interface AccountService {
    public void registerAccount(String username, String password);
    public Optional<AccountDto> logIn(String username, String password);
    public Optional<AccountDto> describe();
}
