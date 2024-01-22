package com.damzxyno.foodstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * This class helps with all admin related http request
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class Admin {
    @GetMapping("/dashboard")
    public String getDashboard(){
        return "dashboard";
    }
}
