package com.damzxyno.foodstore;

import com.damzxyno.foodstore.console_ui.ConsoleApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableMethodSecurity
public class FoodStoreApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(FoodStoreApplication.class, args);
        ConsoleApp consoleApp = context.getBean(ConsoleApp.class);
//        consoleApp.run();
    }

}
