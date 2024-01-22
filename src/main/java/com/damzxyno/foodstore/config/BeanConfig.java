package com.damzxyno.foodstore.config;

import com.damzxyno.foodstore.utilities.PasswordHashingUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Scanner;

/**
 * This class create instantiated beans
 */
@Configuration
public class BeanConfig {
    /**
     * This is a ModelMapper bean
     * @return ModelMapper bean
     */
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    /**
     * This is a Scanner bean
     * @return Scanner bean
     */
    @Bean
    public Scanner scanner(){
        return new Scanner(System.in);
    }

    /**
     * This is a object maper bean
     * @return ObjectMapper bean
     */
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    /**
     * This is a password bean
     * @return PasswordHarshinUtil bean
     */
    @Bean
    public PasswordHashingUtil passwordEncoder(){
        return new PasswordHashingUtil("ENCRYPTO-SALT");
    }

    /**
     * This is a NumberFormat bean for display the British pound suymbol
     * @return NumberFormat bean
     */
    @Bean
    public NumberFormat formatter(){
        Locale ukLocale = new Locale("en", "GB");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(ukLocale);
        currencyFormatter.setCurrency(Currency.getInstance("GBP"));
        return currencyFormatter;
    }
}

