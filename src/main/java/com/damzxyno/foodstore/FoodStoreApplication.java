package com.damzxyno.foodstore;

import com.damzxyno.foodstore.console_ui.ConsoleApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of application
 */

@SpringBootApplication
public class FoodStoreApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(FoodStoreApplication.class, args);
        context.getBean(ConsoleApp.class).run();
    }

}
