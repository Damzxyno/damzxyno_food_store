package com.damzxyno.foodstore.controller;

import com.damzxyno.foodstore.service.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This class takes every customer related requests.
 */
@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @GetMapping()
    public String getAllCustomers(Model model, @RequestParam(required = false, defaultValue = "1") int page){
        var customers = customerService.getAll();
        model.addAttribute("customers", customers);
        return "customers";
    }
}
