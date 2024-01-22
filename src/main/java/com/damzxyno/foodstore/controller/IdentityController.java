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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * This class helps with all identity related http request
 */
@Controller
@RequestMapping("/identity")
@RequiredArgsConstructor
public class IdentityController {
    private final CustomerService customerService;
    private final IdentityService identityService;

    /**
     * This method helps to display a user creation page
     */
    @GetMapping("/create-account")
    public String createAccount(Model model){
        model.addAttribute("customerCreationRequest", new CustomerCreationRequest());
        return "add-user";
    }

    /**
     * This method helps to complete a product creation request
     */
    @PostMapping("/create-account")
    public String createAccount(@ModelAttribute CustomerCreationRequest customerCreationRequest, RedirectAttributes attributes){
        var message = "User created successfully, you may now try to login!";
        attributes.addFlashAttribute("message", message);
        customerService.createCustomer(customerCreationRequest);
        return "redirect:/identity/customer-login";
    }


    /**
     * This method helps to display a customer login page
     */
    @GetMapping("/customer-login")
    public String loginCustomer(Model model){
        model.addAttribute("loginRequest", new LoginRequest());
        return "customer-login";
    }


    /**
     * This method helps to complete a customer login request
     */
    @PostMapping("/customer-login")
    public String loginCustomer(@ModelAttribute LoginRequest request, Model model, RedirectAttributes attributes){
        request.setUserType(UserType.CUSTOMER);
        var res = identityService.login(request);
        if (!res.isSuccess()){
            attributes.addFlashAttribute("message", "Username or password incorrect!");
            return "redirect:customer-login";
        }

        model.addAttribute("loginId", res.getId());
        model.addAttribute("loginRole", res.getUserType());
        return "login-success";
    }

    /**
     * This method helps to display an admin login page
     */
    @GetMapping("/admin-login")
    public String loginAdmin(Model model){
        model.addAttribute("loginRequest", new LoginRequest());
        return "admin-login";
    }

    /**
     * This method helps to complete an admin login request
     */
    @PostMapping("/admin-login")
    public String loginAdmin(@ModelAttribute LoginRequest request, Model model, RedirectAttributes attributes){
        request.setUserType(UserType.ADMIN);
        var res = identityService.login(request);
        if (!res.isSuccess()){
            attributes.addFlashAttribute("message", "Username or password incorrect!");
            return "redirect:admin-login";
        }
        model.addAttribute("loginId", res.getId());
        model.addAttribute("loginRole", res.getUserType());
        return "login-success";
    }
}
