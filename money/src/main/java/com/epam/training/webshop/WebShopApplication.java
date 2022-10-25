package com.epam.training.webshop;

import com.epam.training.webshop.config.ApplicationConfig;
import com.epam.training.webshop.presentation.cli.CliInterpreter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/* Entry point of the application */
public class WebShopApplication {

    public static void main(String[] args) throws IOException {
//        ApplicationConfig.cliInterpreter().start();
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        CliInterpreter cli = context.getBean(CliInterpreter.class);
        cli.start();
    }
}
