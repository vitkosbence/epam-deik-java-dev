package com.epam.training.ticketservice.core.account;

import com.epam.training.ticketservice.core.account.model.AccountDto;
import com.epam.training.ticketservice.core.account.persistence.entity.Account;
import com.epam.training.ticketservice.core.account.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private AccountDto loggedInAccount;

    @Override
    public void registerAccount(String username, String password){
        Account account = new Account(username,password,Account.UserType.NORMAL);
        accountRepository.save(account);
    }

    @Override
    public Optional<AccountDto> logIn(String username, String password){
        Optional<Account> account = accountRepository.findByUsernameAndPassword(username,password);
        if(account.isEmpty()){
            return Optional.empty();
        }
        loggedInAccount = new AccountDto(account.get().getUsername(), account.get().getType());
        return describe();
    }

    @Override
    public Optional<AccountDto> describe(){
        return Optional.ofNullable(loggedInAccount);
    }

    @Override
    public Optional<AccountDto> logOut() {
        Optional<AccountDto> currentAccount = describe();
        loggedInAccount = null;
        return currentAccount;
    }

}
