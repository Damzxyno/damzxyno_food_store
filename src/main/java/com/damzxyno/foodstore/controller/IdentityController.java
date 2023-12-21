package com.damzxyno.foodstore.controller;

import com.damzxyno.foodstore.dto.request.customer.CustomerCreationRequest;
import com.damzxyno.foodstore.dto.request.identity.LoginRequest;
import com.damzxyno.foodstore.enums.UserType;
import com.damzxyno.foodstore.service.interfaces.CustomerService;
import com.damzxyno.foodstore.service.interfaces.IdentityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/identity")
@RequiredArgsConstructor
public class IdentityController {
    private final CustomerService customerService;
    private final IdentityService identityService;
    @GetMapping("/create-account")
    public String createAccount(Model model){
        model.addAttribute("customerCreationRequest", new CustomerCreationRequest());
        return "add-user";
    }

    @PostMapping("/create-account")
    public String createAccount(@ModelAttribute CustomerCreationRequest request){
        customerService.createCustomer(request);
        return "account-creation-success";
    }

    @GetMapping("/customer-login")
    public String login(Model model){
        model.addAttribute("loginRequest", new LoginRequest());
        return "customer-login";
    }

    @PostMapping("/customer-login")
    public String login(@ModelAttribute LoginRequest request, Model model){
        request.setUserType(UserType.CUSTOMER);
        var res = identityService.login(request);
        if (!res.isSuccess()){
            return "redirect:customer-login";
        }
        model.addAttribute("user", res);
        return "login-success";
    }
}
