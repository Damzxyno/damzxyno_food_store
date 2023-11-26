package com.damzxyno.foodstore.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Scanner;

@Configuration
public class BeanConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public Scanner scanner(){
        return new Scanner(System.in);
    }
    @Bean
    public NumberFormat formatter(){
        Locale ukLocale = new Locale("en", "GB");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(ukLocale);
        currencyFormatter.setCurrency(Currency.getInstance("GBP"));
        return currencyFormatter;
    }
}

