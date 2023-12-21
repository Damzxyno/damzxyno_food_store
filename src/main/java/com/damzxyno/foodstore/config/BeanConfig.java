package com.damzxyno.foodstore.config;

import com.damzxyno.foodstore.service.impl.UserInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public NumberFormat formatter(){
        Locale ukLocale = new Locale("en", "GB");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(ukLocale);
        currencyFormatter.setCurrency(Currency.getInstance("GBP"));
        return currencyFormatter;
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
}

