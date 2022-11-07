package com.epam.training.ticketservice.ui.command;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class TestCommand {

    @ShellMethod(key = "test")
    public String test(){
        return "asd";
    }
}
