package com.damzxyno.foodstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * This class helps with all home related http request
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class Home {

    /**
     * This method helps to display the home page all call a user creation page
     */
    @GetMapping
    public String homePage(){
        return "home";
    }
}
