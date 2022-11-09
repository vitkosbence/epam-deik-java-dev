package com.epam.training.ticketservice.core.account.model;

import com.epam.training.ticketservice.core.account.persistence.entity.Account;
import lombok.Value;

@Value
public class AccountDto {

    private final String username;
    private final Account.UserType type;
}
