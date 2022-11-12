package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.account.AccountService;
import com.epam.training.ticketservice.core.account.AccountServiceImpl;
import com.epam.training.ticketservice.core.account.model.AccountDto;
import com.epam.training.ticketservice.core.account.persistence.entity.Account;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class AccountCommand {

    private AccountService accountService;

    @ShellMethod(key = "sign in privileged", value = "Signs into the admin account with a username password combination")
    public String adminSignIn(String username,String password) {
        Optional<AccountDto> account = accountService.logIn(username,password);
        if(account.isEmpty()){
            return "Login failed due to incorrect credentials";
        }
        if(account.get().getType() != Account.UserType.ADMIN){
            return "Sign in successful, but not an admin account";
        }
        return null;
    }

    @ShellMethod(key = "describe account", value = "Displays information about the currently logged in account")
    public String describeAccount() {
        Optional<AccountDto> account = accountService.describe();
        if(account.isEmpty()){
            return "You are not signed in";
        }
        return "Signed in with "+((account.get().getType() == Account.UserType.ADMIN) ? "privileged" : "normal") +
                " account '"+account.get().getUsername()+"'";
    }

    @ShellMethod(key = "sign out", value = "Signs you out from the currently active account")
    public String signOut() {
        accountService.logOut();
        return null;
    }

}
